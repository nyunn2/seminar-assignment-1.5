package com.example.mytictactoe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytictactoe.databinding.ItemGameResultBinding

class GameResultAdapter : ListAdapter<GameResult, GameResultAdapter.GameResultViewHolder>(DiffCallback()) {

    inner class GameResultViewHolder(private val binding: ItemGameResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gameResult: GameResult) {
            // 보드 상태를 각 TextView에 설정
            binding.board1.text = gameResult.board[0].toString()
            binding.board2.text = gameResult.board[1].toString()
            binding.board3.text = gameResult.board[2].toString()
            binding.board4.text = gameResult.board[3].toString()
            binding.board5.text = gameResult.board[4].toString()
            binding.board6.text = gameResult.board[5].toString()
            binding.board7.text = gameResult.board[6].toString()
            binding.board8.text = gameResult.board[7].toString()
            binding.board9.text = gameResult.board[8].toString()

            // 승자 정보 설정
            binding.winnerText.text = when (gameResult.winner) {
                'X' -> "승자는 X 입니다."
                'O' -> "승자는 O 입니다."
                ' ' -> "무승부 입니다."
                else -> "오류"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameResultViewHolder {
        val binding = ItemGameResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameResultViewHolder, position: Int) {
        val gameResult = getItem(position)
        holder.bind(gameResult)
    }

    class DiffCallback : DiffUtil.ItemCallback<GameResult>() {
        override fun areItemsTheSame(oldItem: GameResult, newItem: GameResult): Boolean {
            return oldItem.board contentEquals newItem.board
        }

        override fun areContentsTheSame(oldItem: GameResult, newItem: GameResult): Boolean {
            return oldItem == newItem
        }
    }
}
