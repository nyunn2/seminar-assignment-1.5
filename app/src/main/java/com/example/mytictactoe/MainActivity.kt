package com.example.mytictactoe

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: GameResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        // 각 보드 칸에 클릭 리스너 설정
        setBoardClickListeners()

        // RecyclerView의 gameResultsList 관찰
        viewModel.gameResultsList.observe(this) { results ->
            adapter.submitList(results.toList()) // 게임 결과를 어댑터에 제출
        }
    }

    private fun updateBoardUI(board: Array<Char>) {
        binding.board1.text = board[0].toString()
        binding.board2.text = board[1].toString()
        binding.board3.text = board[2].toString()
        binding.board4.text = board[3].toString()
        binding.board5.text = board[4].toString()
        binding.board6.text = board[5].toString()
        binding.board7.text = board[6].toString()
        binding.board8.text = board[7].toString()
        binding.board9.text = board[8].toString()
    }

    private fun setBoardClickListeners() {
        binding.board1.setOnClickListener { viewModel.checkCurrentState(0) }
        binding.board2.setOnClickListener { viewModel.checkCurrentState(1) }
        binding.board3.setOnClickListener { viewModel.checkCurrentState(2) }
        binding.board4.setOnClickListener { viewModel.checkCurrentState(3) }
        binding.board5.setOnClickListener { viewModel.checkCurrentState(4) }
        binding.board6.setOnClickListener { viewModel.checkCurrentState(5) }
        binding.board7.setOnClickListener { viewModel.checkCurrentState(6) }
        binding.board8.setOnClickListener { viewModel.checkCurrentState(7) }
        binding.board9.setOnClickListener { viewModel.checkCurrentState(8) }
    }
}
