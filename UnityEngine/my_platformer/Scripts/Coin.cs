using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Coin : MonoBehaviour
{
    public GameObject mainCharacter; //компонент, который отвечает за главного героя
    void Start()
    {
        mainCharacter = GameObject.Find("MainCharacter"); //найти компонент главного героя
    }

    // Активация триггера при попадании в него объекта
    void OnTriggerEnter2D(Collider2D collision)
    {
        /**
        * Проверяем, тэг объекта, активировавшего триггер
        * Если его тэг "Player", то условия выполнено
        **/
        if (collision.gameObject.tag == "MainCharacter")
        {
            Destroy(gameObject); //очевидно, уничтожить монетку
            mainCharacter.GetComponent<PlayerMoney>().money_sum += 1; //получить скрипт главного героя и изменить в ней money_sum
        }
    }
}
