/*
 * Copyright (c) 2021 Proton Technologies AG
 * This file is part of Proton Technologies AG and ProtonCore.
 *
 * ProtonCore is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ProtonCore is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ProtonCore.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.proton.core.label.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import me.proton.core.domain.entity.UserId
import me.proton.core.label.domain.entity.LabelId
import me.proton.core.user.data.entity.UserEntity

@Entity(
    primaryKeys = ["userId", "labelId"],
    indices = [
        Index("userId"),
        Index("labelId"),
        Index("parentId"),
        Index("name"),
        Index("type"),
    ],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LabelEntity(
    val userId: UserId,
    val labelId: LabelId,
    val parentId: String?,
    val name: String,
    val type: Int,
    val path: String,
    val color: String,
    val order: Int,
    val isNotified: Boolean?,
    val isExpanded: Boolean?,
    val isSticky: Boolean?,
)
