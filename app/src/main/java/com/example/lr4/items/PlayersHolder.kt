package com.example.lr4.items
import com.example.lr4.R
import com.example.lr4.data.Player

import android.view.View
import android.widget.CheckBox
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

class PlayersHolder(itemView: View): RecyclerView.ViewHolder(itemView)
{
    // каждый элемент RecyclerView содержит текстовое поле для отображения имени и чекбокс
    private val playerName: TextView = itemView.findViewById(R.id.playerNameTextView)
    private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

    // функция, котору вызывает адаптер для каждого элемента RecyclerView
    fun bind(player: Player, playerClickListener: OnPlayerClickListener)
    {
        // для элемента устанавливается значение textView (имя+команда) и чекбокса
        playerName.text = (player.name + player.team)
        checkBox.isChecked = player.isChecked

        // для каждого чекбокса устанавливается слушатель (слушателем будет
        // переданный в параметрах playerClickListener), который получит информацию:
        // экземпляр игрока, который находится рядом с нажатым чекбоксом
        // и собственно текущее значение чекбокса
        checkBox.setOnClickListener()
        {
            playerClickListener.onPlayerClick(player, checkBox.isChecked)
        }
    }
}