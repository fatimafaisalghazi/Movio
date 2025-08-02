package com.madrid.designSystem.component.BottomSheet

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class UserList(
    val id: String,
    val name: String,
    var isSelected: Boolean = false,
    var isLoading: Boolean = false
)

@Composable
fun UserListItem(
    userList: UserList,
    onToggleSelection: (UserList) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(
                enabled = !userList.isLoading,
                role = Role.Checkbox,
                onClickLabel = if (userList.isSelected) "Remove from list" else "Add to list"
            ) { onToggleSelection(userList) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MovioText(
            text = userList.name,
            textStyle = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier.size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                userList.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                }

                userList.isSelected -> {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        MovioIcon(
                            painter = painterResource(id = R.drawable.bold_check_circle),
                            contentDescription = "Selected",
                            tint = Color(0xFF8B5CF6),
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                else -> {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .border(
                                width = 2.dp,
                                color = Theme.color.surfaces.onSurfaceContainer,
                                shape = CircleShape
                            )
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        MovioIcon(
                            painter = painterResource(id = R.drawable.add),
                            tint = Theme.color.surfaces.onSurfaceContainer,
                            contentDescription = "Add to list",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CreateNewListItem(
    onListCreated: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(
                role = Role.Button,
                onClickLabel = "Create a new list"
            ) { onListCreated() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            MovioIcon(
                painter = painterResource(id = R.drawable.add),
                contentDescription = "Create new list",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        MovioText(
            text = "Create a new list",
            textStyle = MaterialTheme.typography.bodyLarge,
            color = Color.White,
        )
    }
}

@Composable
fun AddToListBottomSheetContent(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onListCreated: () -> Unit,
    initialUserLists: List<Any>,
    onSelectionChanged: ((UserList, Boolean) -> Unit)? = null
) {
    val userLists = remember { mutableStateListOf(*initialUserLists.toTypedArray()) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 0.dp,
                vertical = 16.dp
            )
    ) {
        CreateNewListItem(
            onListCreated = onListCreated
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (userLists.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                items(userLists) { userList ->
                    UserListItem(
                        userList = if (userList is UserList) userList else UserList(
                            id = "unknown",
                            name = "Unknown List",
                            isSelected = false,
                            isLoading = false
                        ),
                        onToggleSelection = { toggledList ->
                            val index = userLists.indexOf(toggledList)
                            if (index != -1 && !toggledList.isLoading) {
                                // Set loading state
                                userLists[index] = toggledList.copy(isLoading = true)

                                // Use proper coroutine scope instead of GlobalScope
                                coroutineScope.launch {
                                    try {
                                        // Simulate network call
                                        delay(1500)

                                        val newSelectionState = !toggledList.isSelected
                                        userLists[index] = toggledList.copy(
                                            isSelected = newSelectionState,
                                            isLoading = false
                                        )

                                        // Notify parent about selection change
                                        onSelectionChanged?.invoke(toggledList, newSelectionState)

                                        println("${toggledList.name} is now ${if (newSelectionState) "selected" else "deselected"}")
                                    } catch (e: Exception) {
                                        // Handle error case
                                        userLists[index] = toggledList.copy(isLoading = false)
                                        println("Error updating list: ${e.message}")
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true , backgroundColor = 0xFF1A1B23)
@Composable
fun PreviewAddToListBottomSheetContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1A1B23)
            )
        ) {
            AddToListBottomSheetContent(
                onListCreated = { println("Create new list clicked") },
                initialUserLists = listOf(
                    UserList("1", "Watch later", isSelected = true),
                    UserList("2", "Watching after exam", isSelected = false),
                    UserList("3", "Watching soon", isSelected = false),
                    UserList("4", "Adventure movies", isSelected = false)
                ),
                onSelectionChanged = { userList, isSelected ->
                    println("Selection changed: ${userList.name} -> $isSelected")
                }
            )
        }
    }
}