using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ChangeMusic : MonoBehaviour
{
    public AudioClip battleMusic;

    // Активация триггера при попадании в него объекта
    void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.gameObject.tag == "MainCharacter")
        {
            MusicController.Instance.PlayMusic(battleMusic);
            Destroy(gameObject);
        }
    }
}