package com.example.lr4.items
import com.example.lr4.data.Player

interface OnPlayerClickListener
{
    fun onPlayerClick(player: Player, isChecked: Boolean)
}