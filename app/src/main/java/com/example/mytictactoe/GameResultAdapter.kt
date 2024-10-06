package com.example.mytictactoe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytictactoe.databinding.ItemGameResultBinding

class GameResultAdapter() : ListAdapter<GameResult, GameResultAdapter.GameResultViewHolder>(DiffCallback()) {

    inner class GameResultViewHolder(private val binding: ItemGameResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gameResult: GameResult) {
            /*
            val boardViews = mutableListOf<View>()
            for (i in 0 until 25) {
                boardViews.add(binding.gameresultBoard.getChildAt(i))
            }
            // 보드 상태를 각 View에 설정
            boardViews.forEachIndexed { index, view ->
                if (view is Button) {
                    view.text = gameResult.board[index].toString()
                }
            }

             */

            for (i in 0 until 25) {
                val button = Button(binding.root.context).apply {
                    text = gameResult.board[i].toString()
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 0
                        height = 0
                        rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                        columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    }
                    textSize = 8f
                }
                binding.gameresultBoard.addView(button)
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
