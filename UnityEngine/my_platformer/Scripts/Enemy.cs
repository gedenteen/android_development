using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Enemy : MonoBehaviour
{
    public AudioClip soundHurt;

    public void EnemyHurt()
    {
        MusicController.Instance.Play(soundHurt);
        Destroy(this.gameObject);
    }
}
