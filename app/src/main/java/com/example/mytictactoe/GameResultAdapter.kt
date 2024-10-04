package com.example.mytictactoe

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytictactoe.databinding.ItemGameResultBinding

class GameResultAdapter(
    var historyButtons: List<Button>
) : ListAdapter<GameResult, GameResultAdapter.GameResultViewHolder>(DiffCallback()) {

    inner class GameResultViewHolder(private val binding: ItemGameResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gameResult: GameResult) {
            // 보드 상태를 각 TextView에 설정
            historyButtons.forEachIndexed { index, button ->
                button.text = gameResult.board[index].toString()
            }

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
