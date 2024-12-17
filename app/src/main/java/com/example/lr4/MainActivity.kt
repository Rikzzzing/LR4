package com.example.lr4
import com.example.lr4.data.Player
import com.example.lr4.items.PlayersAdapter
import com.example.lr4.items.OnPlayerClickListener
import com.example.lr4.databinding.ActivityMainBinding

import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import kotlin.random.Random

// тут наследуемся от интерфейса OnPlayerClickListener
class MainActivity : AppCompatActivity(), OnPlayerClickListener
{
    private lateinit var binding: ActivityMainBinding
    private lateinit var playersAdapter: PlayersAdapter
    private var players: ArrayList<Player> = ArrayList()
    private lateinit var mainPageRecyclerView: androidx.recyclerview.widget.RecyclerView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        // как обычно подключаем биндинг
        binding = ActivityMainBinding.inflate(layoutInflater)
        // получаем view как binding.root
        val view: View = binding.root

        // получаем RecyclerView из activity_main.xlm
        mainPageRecyclerView = binding.mainPageRecyclerView

        // создаем PlayersAdapter, передавая в него список, который нужно отобразить
        // в RecyclerView и себя в качестве слушателя для нажатия на чекбокс
        playersAdapter = PlayersAdapter(players, this)

        // настройки RecyclerView
        mainPageRecyclerView.layoutManager = LinearLayoutManager(this)
        mainPageRecyclerView.setHasFixedSize(false)
        mainPageRecyclerView.adapter = playersAdapter

        // прослушивание кнопок добавления игрока, удаления выбранных игроков и формирования команд
        binding.btnAdd.setOnClickListener { add(view) }
        binding.btnDelete.setOnClickListener { delete(view) }
        binding.btnStart.setOnClickListener { start(view) }

        setContentView(view)
    }

    private fun add(view: View)
    {
        if (binding.nameEditText.text.isNotEmpty())
        {
            // к текущему списку игроков добавляем игрока с введенным именем
            players.add(Player(name = binding.nameEditText.text.toString(), isChecked = false))

            // для того, чтобы обновление адаптера произошло успешно, нужно
            // создать копию списка players и передать его как параметр для updateAdapter()
            // (newPlayersList = players НЕ ПРОКАТИТ, потому что список это ссылочный тип)
            val newPlayersList: ArrayList<Player> = ArrayList()
            newPlayersList.addAll(players)

            // собственно, обновление адаптера
            playersAdapter.updateAdapter(newPlayersList)

            // очистка поля с именем
            binding.nameEditText.text.clear()
        }
    }

    private fun delete(view: View)
    {
        // создаем пустой список
        val newPlayersList: ArrayList<Player> = ArrayList()

        // проходимся по всем игрокам в players и в newPlayersList кладем только тех,
        // у кого поле isChecked=false (т.е. неотмеченные чекбоксы) - остальных (отмеченных)
        // нам нужно удалить
        for (player in players)
        {
            if (!player.isChecked)
            {
                newPlayersList.add(player)
            }
        }

        // собственно, обновление адаптера
        playersAdapter.updateAdapter(newPlayersList)
    }

    private fun start(view: View)
    {
        // создаем пустой список
        val newPlayersList: ArrayList<Player> = ArrayList()

        // проходимся по всем игрокам в players и в newPlayersList кладем только тех,
        // у кого поле isChecked=true (т.е. отмеченные чекбоксы) - команды будут формироваться
        // только для них (по условию задачи - они типо присутствующие)
        for (player in players)
        {
            if (player.isChecked)
            {
                newPlayersList.add(player)
            }
        }

        // определяем максимально допустимое кол-во человек в команде
        // и текущее кол-во человек в каждой их двух команд
        val maxPlayersInTeam = newPlayersList.size / 2
        var playersInRedTeam = 0
        var playersInGreenTeam = 0

        // проходимся по всем игрокам в players
        for (player in players)
        {
            // если игрок присутствует (т.е. есть в списке newPlayersList)
            if (newPlayersList.indexOf(player) != -1)
            {
                if (Random.nextBoolean())
                {
                    if (playersInRedTeam < maxPlayersInTeam)
                    {
                        player.team = "(Красный)"
                        playersInRedTeam++
                    }
                    else
                    {
                        player.team = "(Зеленый)"
                        playersInGreenTeam++
                    }
                }
                else
                {
                    if (playersInGreenTeam < maxPlayersInTeam)
                    {
                        player.team = "(Зеленый)"
                        playersInGreenTeam++
                    }
                    else
                    {
                        player.team = "(Красный)"
                        playersInRedTeam++
                    }
                }
            }
        }

        // для того, чтобы обновление адаптера произошло успешно, нужно
        // создать копию списка players и передать его как параметр для updateAdapter()
        // (newPlayersList = players НЕ ПРОКАТИТ, потому что список это ссылочный тип)
        newPlayersList.clear()
        newPlayersList.addAll(players)

        // собственно, обновление адаптера (рядом с именем игрока должна отобразиться его команда)
        playersAdapter.updateAdapter(newPlayersList)
    }

    // реализация метода интерфейса OnPlayerClickListener
    // при каждом нажатии чекбокса мы получаем игрока, рядом с которым был нажат чекбокс
    // и значение чекбокса (галочка есть = true)
    override fun onPlayerClick(player: Player, isChecked: Boolean)
    {
        // у игрока, рядом с которым был нажат чекбокс меняем поле isChecked
        // на то значение, что и у чекбокса
        players[players.indexOf(player)].isChecked = isChecked
    }
}