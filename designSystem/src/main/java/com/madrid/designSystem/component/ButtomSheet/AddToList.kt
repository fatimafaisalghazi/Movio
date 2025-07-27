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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            .height(56.dp) // Match design height
            .clickable(
                enabled = !userList.isLoading, // Disable clicking when loading
                role = Role.Checkbox,
                onClickLabel = if (userList.isSelected) "Remove from list" else "Add to list"
            ) { onToggleSelection(userList) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = userList.name,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White, // White text as shown in design
            modifier = Modifier.weight(1f)
        )

        // Selection indicator matching Figma design
        Box(
            modifier = Modifier.size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                userList.isLoading -> {
                    // Loading state - circular progress indicator
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White.copy(alpha = 0.7f),
                        strokeWidth = 2.dp,
                        strokeCap = StrokeCap.Round
                    )
                }

                userList.isSelected -> {
                    // Selected state - purple circle with white checkmark
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier.size(32.dp),
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF8B5CF6) // Purple color from design
                            )
                        ) {
                            Box(
                                modifier = Modifier.size(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }

                else -> {
                    // Unselected state - outlined circle with plus
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .border(
                                width = 1.dp,
                                color = Color.White.copy(alpha = 0.3f),
                                shape = CircleShape
                            )
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.size(18.dp)
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
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = "Create a new list",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}

@Composable
fun AddToListBottomSheetContent(
    modifier: Modifier = Modifier,
    onListCreated: () -> Unit,
    initialUserLists: List<UserList>,
    onSelectionChanged: ((UserList, Boolean) -> Unit)? = null
) {
    val userLists = remember { mutableStateListOf(*initialUserLists.toTypedArray()) }

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

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Existing lists
        if (userLists.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                items(userLists) { userList ->
                    UserListItem(
                        userList = userList,
                        onToggleSelection = { toggledList ->
                            val index = userLists.indexOf(toggledList)
                            if (index != -1 && !toggledList.isLoading) {
                                userLists[index] = toggledList.copy(isLoading = true)
                                kotlinx.coroutines.GlobalScope.launch {
                                    kotlinx.coroutines.delay(1500)
                                    val newSelectionState = !toggledList.isSelected
                                    userLists[index] = toggledList.copy(
                                        isSelected = newSelectionState,
                                        isLoading = false
                                    )

                                    onSelectionChanged?.invoke(toggledList, newSelectionState)

                                    println("${toggledList.name} is now ${if (newSelectionState) "selected" else "deselected"}")
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAddToListBottomSheetContent() {
    // Dark background to match the design
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1A1B23) // Dark background from design
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