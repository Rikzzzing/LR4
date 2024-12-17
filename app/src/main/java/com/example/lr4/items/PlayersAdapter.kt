package com.example.lr4.items
import com.example.lr4.R
import com.example.lr4.data.Player

import android.view.ViewGroup
import android.view.LayoutInflater

import androidx.recyclerview.widget.RecyclerView

// адаптер получает список игроков, которые ему нужно отобразить в RecyclerView и слушателя, который будет обрабатывать нажатия чекбоксов
class PlayersAdapter(private val players: ArrayList<Player>, private val playerClickListener: OnPlayerClickListener): RecyclerView.Adapter<PlayersHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersHolder
    {
        val viewLayout = LayoutInflater.from(parent.context).inflate(R.layout.player_item, parent,false)
        return PlayersHolder(viewLayout)
    }

    override fun getItemCount(): Int
    {
        return players.size
    }

    override fun onBindViewHolder(holder: PlayersHolder, index: Int)
    {
        // вызываем функцию в холдере для каждого элемента в RecyclerView
        // при этом епердаем слушателя (которого получил адаптер в конструкторе)
        holder.bind(players[index], playerClickListener)
    }

    // функция обновления адаптера
    fun updateAdapter(newPlayersList: ArrayList<Player>)
    {
        // очищаем имеющийся список и получаем новый
        players.clear()
        players.addAll(newPlayersList)

        // вроде, функция, обновляющая отрисовку
        notifyDataSetChanged()
    }
}