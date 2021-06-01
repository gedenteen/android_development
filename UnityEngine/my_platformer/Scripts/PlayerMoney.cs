using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class PlayerMoney : MonoBehaviour
{
	public int money_sum; // переменная для монет 

	[SerializeField]
	public Text TextMoney; //в данную ссылку будем переносить текстовую информацию о монетах


	void Start()
	{
		money_sum = 0; // по умолчанию, при запуске сцены количество монет = 0
	}

	void OnGUI()
	{
		//GUI.Label(new Rect(10, 10, 100, 100), "Score: " + money_sum.ToString());
		TextMoney.text = money_sum.ToString();
	}
}
