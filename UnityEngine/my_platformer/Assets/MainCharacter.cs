using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class MainCharacter : MonoBehaviour
{
    /**
    ** Ускорение игрока
    **/
    /*[Header("Player velocity")]
    // Ось Ox
    public int xVelocity = 7;
    // Ось Oy
    public int yVelocity = 10;*/
    public Vector2 playerVelocity = new Vector2(7, 7);

    // слой земли с foreground
    [SerializeField] private LayerMask ground;
    // Физическое поведение (тело) объекта
    private Rigidbody2D rigidBody2dComponent;
    // Коллайдер, проверка на столкновения
    private Collider2D collider2dComponent;
    // доступ к спрайту
    private SpriteRenderer spriteRendererComponent;
    // доступ к анимации
    private Animator animatorComponent;
    // джойстик для управления движением
    public Joystick joystick;
    // 
    [SerializeField] public AudioClip audioClip; // звук прыжка
    private AudioSource audioSource; // объявляем компонент аудио источника
    // hitpoints:
    public int hitpoints = 3;
    [SerializeField] private Image[] hearts;
    [SerializeField] private Sprite aliveHeart;
    [SerializeField] private Sprite deadHeart;


    //                              0      1        2        3
    private enum AnimationState { idle, running, jumping, falling };
    // Значением по-умолчанию ставим idle (0), как мы и задавали в аниматоре
    private AnimationState currentAnimationState = AnimationState.idle;


    // Start (https://docs.unity3d.com/ScriptReference/MonoBehaviour.Start.html)
    private void Start()
    {
        // Получаем доступ к Rigidbody2D объекта Player
        rigidBody2dComponent = gameObject.GetComponent<Rigidbody2D>();
        collider2dComponent = gameObject.GetComponent<Collider2D>();
        spriteRendererComponent = gameObject.GetComponent<SpriteRenderer>();
        animatorComponent = gameObject.GetComponent<Animator>();
        audioSource = GetComponent<AudioSource>();
    }

    private void Update()
    {
        updatePlayerPosition();
    }

    //FIXED UPDATE??

    bool inAir;
    // Обновляем местоположение игрока
    private void updatePlayerPosition()
    {
        float horizontaMoveInput = joystick.Horizontal;//Input.GetAxis("Horizontal");
        float jumpInput = joystick.Vertical;//Input.GetAxis("Jump");

        // Движение влево
        if (horizontaMoveInput < 0)
        {
            rigidBody2dComponent.velocity = new Vector2(-playerVelocity.x, rigidBody2dComponent.velocity.y);
            spriteRendererComponent.flipX = true;
            // Движение вправо  
        }
        else if (horizontaMoveInput > 0)
        {
            rigidBody2dComponent.velocity = new Vector2(playerVelocity.x, rigidBody2dComponent.velocity.y);
            spriteRendererComponent.flipX = false;
            // Если персонаж стоит на земле и не двигается, отключаем инерцию
        }
        else if (collider2dComponent.IsTouchingLayers(ground))
        {
            rigidBody2dComponent.velocity = Vector2.zero;
        }

        // Если нажата клавиша прыжка и персонаж касается земли - прыгаем
        if (jumpInput > 0.5 && !inAir)//collider2dComponent.IsTouchingLayers(ground))
        {
            inAir = true;
            rigidBody2dComponent.velocity = new Vector2(rigidBody2dComponent.velocity.x, playerVelocity.y);
            audioSource.Play();//PlayOneShot(audioClip, 0.4f);
        }

        // Вызываем менеджер анимаций
        SetAnimationState();
    }

    // Выбираем текущую анимацию
    private void SetAnimationState()
    {
        // Персонаж касается земли 
        if (collider2dComponent.IsTouchingLayers(ground))
        {
            // При помощи Mathf.Abs получаем модуль значения ускорения (если бежим влево, оно отрицательное)
            // Если оно больше 0.1 (не стоим на месте), то персонаж бежит
            if (Mathf.Abs(rigidBody2dComponent.velocity.x) > 0.1f)
            {
                currentAnimationState = AnimationState.running;
            }
            else
            {
                // Если нет - стоим на месте  
                currentAnimationState = AnimationState.idle;
            }
            // Персонаж не касается земли  
        }
        else
        {
            // Ставим текущей анимацией прыжок
            currentAnimationState = AnimationState.jumping;

            if (currentAnimationState == AnimationState.jumping)
            {
                // Если усорение уходит в отрицательное значение, значит персонаж падает вниз
                if (rigidBody2dComponent.velocity.y < .1f)
                {
                    currentAnimationState = AnimationState.falling;
                }
            }
            else if (currentAnimationState == AnimationState.falling)
            {
                // Если он коснулся земли, то персонаж переходит в состояние спокойствия
                if (collider2dComponent.IsTouchingLayers(ground))
                {
                    currentAnimationState = AnimationState.idle;
                }
            }
        }

        // Изменияем значение state в аниматоре
        animatorComponent.SetInteger("state", (int)currentAnimationState);
    }

    void Hurt()
    {
        hitpoints--;
        if (hitpoints <= 0)
            SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
        for (int i = 1; i <= hitpoints; i++)
        {
            hearts[i].sprite = aliveHeart;
        }
        for (int i = hitpoints; i <= 3; i++)
        {
            hearts[i].sprite = deadHeart;
            //hearts[i].enabled = false;
        }
    }

    void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.gameObject.layer == LayerMask.NameToLayer("Ground"))
        {
            inAir = false;
            playerVelocity.x = 7;
            return;
        }
        if (collision.gameObject.layer == LayerMask.NameToLayer("InvisibleWater"))
        { //если столкнулся с невидимой водой на "дне" уровня, то запустить уровень заново
            hitpoints = 3;
            SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
            return;
        }
        if (collision.gameObject.layer == LayerMask.NameToLayer("IceLayer"))
        { //если столкнулся с невидимой водой на "дне" уровня, то запустить уровень заново
            playerVelocity.x = 17;
            return;
        }

        Enemy enemy = collision.collider.GetComponent<Enemy>();
        if (enemy != null)
        {
            foreach (ContactPoint2D point in collision.contacts)
            {
                if (point.normal.y >= 0.6f)
                {
                    enemy.EnemyHurt();
                }
                else
                {
                    Hurt();
                }
            }
        }

    }
}
