package com.example.mytictactoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//현재 플레이어, 승패(승리, 무승부), 초기화, 공지사항
class MainViewModel : ViewModel() {
    private val _currentPlayer = MutableLiveData<Char>('O')
    private val _announcement = MutableLiveData<String>("O의 차례입니다")
    private val _resetButton = MutableLiveData<String>("초기화")
    private val _board = MutableLiveData<Array<Char>>(Array(25) {' '})
    private val _playing = MutableLiveData<Boolean>(true)

    private val _gameResultsList = MutableLiveData<List<GameResult>>()
    val gameResultsList: LiveData<List<GameResult>> get() = _gameResultsList
    private val gameResults = mutableListOf<GameResult>()

    private var num = 0

    val currentPlayer: LiveData<Char> get() = _currentPlayer
    val announcement: LiveData<String> get() = _announcement
    val resetButton: LiveData<String> get() = _resetButton
    val board: LiveData<Array<Char>> get() = _board
    val playing: LiveData<Boolean> get() = _playing

    var winnerdata = MutableLiveData<Char>('I')

    fun checkCurrentState(position: Int) {
        if (_playing.value!! && board.value!![position] == ' ') {
            val newBoard: Array<Char> = _board.value!!.clone()
            newBoard[position] = _currentPlayer.value!!
            _board.value = newBoard

            if (checkVictory(newBoard, _currentPlayer.value!!)) {
                winnerdata.value = _currentPlayer.value!!
                _resetButton.value = "한판 더"
                _announcement.value = "게임 오버"
                _playing.value = false
            } else {
                num++
                if (num == 25) {
                    winnerdata.value = ' '
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

    fun addNewGameResult(board: Array<Char>, winner: Char) {
        val winPlayer = winner // 승자가 없으면 무승부
        val ThisGameResult = GameResult(board, winPlayer) // GameResult 객체 생성
        gameResults.add(ThisGameResult) // 리스트에 추가
        _gameResultsList.value = gameResults.toList() // MutableLiveData 업데이트
    }

    fun checkVictory(board: Array<Char>, player: Char): Boolean {
        val size = 5
        val winCount = 5 // 승리 조건

        // 가로
        for (i in 0 until size) {
            for (j in 0 until size - winCount + 1) {
                if ((0 until winCount).all { k -> board[i * size + (j + k)] == player }) {
                    return true
                }
            }
        }
        // 세로
        for (j in 0 until size) {
            for (i in 0 until size - winCount + 1) {
                if ((0 until winCount).all { k -> board[(i + k) * size + j] == player }) {
                    return true
                }
            }
        }
        // 대각선
        for (i in 0 until size - winCount + 1) {
            for (j in 0 until size - winCount + 1) {
                if ((0 until winCount).all { k -> board[(i + k) * size + (j + k)] == player }) {
                    return true
                }
            }
        }
        // 대각선
        for (i in winCount - 1 until size) {
            for (j in 0 until size - winCount + 1) {
                if ((0 until winCount).all { k -> board[(i - k) * size + (j + k)] == player }) {
                    return true
                }
            }
        }

        return false
    }

    fun resetGame() {
        _playing.value = true
        _currentPlayer.value = 'O'
        _announcement.value = "O의 차례입니다"
        _resetButton.value = "초기화"
        _board.value = Array(25) { ' ' }
        num = 0
    }
}