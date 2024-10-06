package com.example.mytictactoe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytictactoe.databinding.ActivityMainBinding
import com.example.mytictactoe.databinding.ItemGameResultBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: GameResultAdapter
    private lateinit var buttons: List<Button>
    private lateinit var historyButtons: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 오목 판 생성
        //createDrawerBoard() // 서랍 오목판
        createBoard()

        // RecyclerView 초기화
        adapter = GameResultAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this) // 세로로 나열
        binding.recyclerView.adapter = adapter

        // ViewModel의 LiveData 관찰
        viewModel.currentPlayer.observe(this) {
            binding.announcement.text = viewModel.announcement.value
            binding.resetButton.text = if (viewModel.playing.value == true) "초기화" else "한판 더"
        }

        viewModel.board.observe(this) { board ->
            updateBoardUI(board)
        }

        viewModel.playing.observe(this) { isPlaying ->
            if (!isPlaying) {
                binding.announcement.text = viewModel.announcement.value
                binding.resetButton.text = viewModel.resetButton.value

                // 게임이 끝났을 때 결과를 추가
                viewModel.addNewGameResult(viewModel.board.value!!,viewModel.winnerdata.value!!) // 현재 보드 추가
            }
        }

        binding.resetButton.setOnClickListener {
            viewModel.resetGame()
        }


        // RecyclerView의 gameResultsList 관찰
        viewModel.gameResultsList.observe(this) { results ->
            adapter.submitList(results.toList()) // 게임 결과를 어댑터에 제출
        }
    }

    private fun createDrawerBoard() {
        val itemGameResultLayout = ItemGameResultBinding.inflate(layoutInflater).gameresultBoard
        itemGameResultLayout.rowCount = 5
        itemGameResultLayout.columnCount = 5

        historyButtons = (0..24).map { i ->
            val button = Button(this).apply {
                id = View.generateViewId()
                text = i.toString()
                textSize = 8f
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = 0
                    rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }
            }
            itemGameResultLayout.addView(button)
            Log.d("createDrawerBoard", "Button added: $button with text: ${button.text}")
            button
        }
    }


    private fun createBoard() {
        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
        gridLayout.rowCount = 5
        gridLayout.columnCount = 5

        buttons = (0..24).map { i ->
            val button = Button(this).apply {
                id = View.generateViewId()
                text = i.toString()
                textSize = 15f
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = 0
                    rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }
            }
            gridLayout.addView(button)
            button.setOnClickListener {
                viewModel.checkCurrentState(i)
            }
            button
        }
    }

    private fun updateBoardUI(board: Array<Char>) {
        buttons.forEachIndexed { index, button ->
            button.text = board[index].toString()
        }
    }
}
