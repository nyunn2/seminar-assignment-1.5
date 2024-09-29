package com.example.mytictactoe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytictactoe.databinding.ItemGameResultBinding

class GameResultAdapter : ListAdapter<Array<Char>, GameResultAdapter.GameResultViewHolder>(DiffCallback()) {

    inner class GameResultViewHolder(private val binding: ItemGameResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gameResult: Array<Char>) {
            binding.board1.text = gameResult[0].toString()
            binding.board2.text = gameResult[1].toString()
            binding.board3.text = gameResult[2].toString()
            binding.board4.text = gameResult[3].toString()
            binding.board5.text = gameResult[4].toString()
            binding.board6.text = gameResult[5].toString()
            binding.board7.text = gameResult[6].toString()
            binding.board8.text = gameResult[7].toString()
            binding.board9.text = gameResult[8].toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameResultViewHolder {
        val binding = ItemGameResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameResultViewHolder, position: Int) {
        val gameResult = getItem(position) // Use getItem to retrieve the game result
        holder.bind(gameResult)
    }

    class DiffCallback : DiffUtil.ItemCallback<Array<Char>>() {
        override fun areItemsTheSame(oldItem: Array<Char>, newItem: Array<Char>): Boolean {
            return oldItem contentEquals newItem // Check if both items are the same
        }

        override fun areContentsTheSame(oldItem: Array<Char>, newItem: Array<Char>): Boolean {
            return oldItem contentEquals newItem // Check if their contents are the same
        }
    }
}
