package ir.batna.parsetest.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.batna.parsetest.model.Request
import ir.batna.parsetest.viewmodel.MainViewModel

class NoteUi {
    @Composable
    fun NoteList(
        viewModel: MainViewModel,
        onClickListItem: () -> Unit
    ) {
        viewModel.refreshData()
        val parseModel by viewModel.requestData.collectAsState()


        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth()
            )
            {
                LazyColumn(
                    modifier = Modifier
                        .padding(10.dp)

                ) {
                    items(parseModel) { it ->
                        listView(it, viewModel, onClickListItem)
                    }
                }

            }
            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(10.dp)
                    .padding(end = 20.dp, bottom = 10.dp)
            ) {
                ExtendedExample() {
                    onClickListItem()
                }
            }
        }
    }

    @Composable
    fun ExtendedExample(onClick: () -> Unit) {
        ExtendedFloatingActionButton(
            onClick = { onClick() },
            icon = { Icon(Icons.Filled.Edit, "Extended floating action button.") },
            text = { Text(text = "New Note") },
            containerColor = Color(0xFFEFB8C8)
        )
    }

    @Composable
    fun listView(
        request: Request, viewModel: MainViewModel,
        onClickListItem: () -> Unit
    ) {

        var expanded by remember { mutableStateOf(false) }
        val extraPadding by animateDpAsState(
            if (expanded) 48.dp else 0.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ), label = ""
        )

        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Row(modifier = Modifier
                .padding(24.dp)
                .clickable {

//                viewModel.clickOnNote(noteId = noteModel.id)
                    onClickListItem()

                }) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                ) {
                    Text(text = request.title)
                    Text(if (expanded) request.body else "")
                    Text(
                        text = request.lastEdite, modifier = Modifier
                            .padding(bottom = 0.dp)
                            .align(Alignment.End),
                        style = TextStyle(fontWeight = FontWeight.Light, fontSize = 10.sp)
                    )
                }
                ElevatedButton(
                    onClick = { expanded = !expanded }
                ) {
                    Text(if (expanded) "Show less" else "Show more")
                }
            }
        }
    }
}