package com.androidtetris.ui.screens

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.androidtetris.R
import com.androidtetris.TetrisScreenViewModel
import com.androidtetris.game.Direction
import com.androidtetris.ui.components.GameActionButton
import com.androidtetris.ui.components.TetrisGrid

/* AndroidTetris TetrisScreen: the composable that actually displays the gameplay */

@Composable
fun TetrisScreen() {
    val viewModel by remember { mutableStateOf(TetrisScreenViewModel()) }
    val columnPadding = 16
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(columnPadding.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.weight(0.3f)
            ) {
                // Left side column, contains upcoming tetrominoes grid, stats, ghost chip
                /*UpcomingTetrominoesBox(
                    width = 120.dp,
                    height = 200.dp,
                    modifier = Modifier.padding(bottom = 32.dp)
                )*/
                Stats(viewModel)
                Row(modifier = Modifier.padding(top = 32.dp)) {
                    Switch(
                        checked = true,
                        onCheckedChange = {},
                    )
                }
            }
            Column(
                modifier = Modifier.weight(0.7f),
                horizontalAlignment = Alignment.End
            ) {
                // Right side column, contains the tetris game grid
                val fraction = 0.8f
                TetrisGrid(
                    width = 200.dp,
                    height = 440.dp,
                    viewModel = viewModel
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = fraction)
                        .padding(top = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    GameActionButton(
                        drawable = R.drawable.arrow_up,
                        actionDelay = 80L,
                        onActionDown = { viewModel.rotate() }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(fraction = fraction),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    GameActionButton(
                        drawable = R.drawable.arrow_left,
                        onActionDown = { viewModel.move(Direction.Left) }
                    )
                    GameActionButton(
                        drawable = R.drawable.arrow_right,
                        onActionDown = { viewModel.move(Direction.Right) }
                    )
                }
                Box(
                    modifier = Modifier.fillMaxWidth(fraction = fraction),
                    contentAlignment = Alignment.Center
                ) {
                    GameActionButton(
                        drawable = R.drawable.arrow_down,
                        onActionDown = { viewModel.move(Direction.Down) }
                    )
                }
            }
        }
    }
}

@Composable
private fun Stats(
    viewModel: TetrisScreenViewModel,
    modifier: Modifier = Modifier
) {
    val gameStats by remember { derivedStateOf { viewModel.statsState } }
    Column(modifier = modifier) {
        Text("Lines: ${gameStats.lines}")
        Text("Score: ${gameStats.score}")
        Text("Level: ${gameStats.level}")
    }
}