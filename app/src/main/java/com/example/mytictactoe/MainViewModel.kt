package com.example.mytictactoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//현재 플레이어, 승패(승리, 무승부), 초기화, 공지사항
class MainViewModel : ViewModel() {
    private val _currentPlayer = MutableLiveData<Char>('O')
    private val _announcement = MutableLiveData<String>("O의 차례입니다")
    private val _resetButton = MutableLiveData<String>("초기화")
    private val _board = MutableLiveData<Array<Char>>(Array(9) {' '})
    private val _playing = MutableLiveData<Boolean>(true)

    private val _gameResultsList = MutableLiveData<List<Array<Char>>>()
    val gameResultsList: LiveData<List<Array<Char>>> get() = _gameResultsList
    private val gameResults = mutableListOf<Array<Char>>()

    private var num = 0

    val currentPlayer: LiveData<Char> get() = _currentPlayer
    val announcement: LiveData<String> get() = _announcement
    val resetButton: LiveData<String> get() = _resetButton
    val board: LiveData<Array<Char>> get() = _board
    val playing: LiveData<Boolean> get() = _playing

    fun checkCurrentState(position: Int) {
        if (_playing.value!! && board.value!![position] == ' ') {
            val newBoard: Array<Char> = _board.value!!.clone() // 기존 보드를 복사
            newBoard[position] = _currentPlayer.value!! // 현재 플레이어의 마크를 설정
            _board.value = newBoard

            if (checkVictory(newBoard)) {
                _resetButton.value = "한판 더"
                _announcement.value = "게임 오버"
                _playing.value = false
            } else {
                num++
                if (num == 9) {
                    _resetButton.value = "한판 더"
                    _announcement.value = "무승부"
                    _playing.value = false
                } else {
                    if (_currentPlayer.value!! == 'O') {
                        _currentPlayer.value = 'X'
                        _announcement.value = "X의 차례입니다"
                    } else {
                        _currentPlayer.value = 'O'
                        _announcement.value = "O의 차례입니다"
                    }
                }
            }
        }
    }

    fun addNewGameResult(board: Array<Char>) {
        gameResults.add(board)
        _gameResultsList.value = gameResults.toList()
    }

    fun checkVictory(board: Array<Char>): Boolean {
        val victory = arrayOf(
            arrayOf(0, 1, 2),
            arrayOf(3, 4, 5),
            arrayOf(6, 7, 8),
            arrayOf(0, 3, 6),
            arrayOf(1, 4, 7),
            arrayOf(2, 5, 8),
            arrayOf(0, 4, 8),
            arrayOf(2, 4, 6)
        )
        return victory.any { (a, b, c) -> board[a] == board[b] && board[b] == board[c] && board[a] != ' ' }
    }

    fun resetGame() {
        _playing.value = true
        _currentPlayer.value = 'O'
        _announcement.value = "O의 차례입니다"
        _resetButton.value = "초기화"
        _board.value = Array(9) { ' ' }
        num = 0
    }
}