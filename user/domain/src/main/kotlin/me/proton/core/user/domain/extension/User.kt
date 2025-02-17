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

package me.proton.core.user.domain.extension

import me.proton.core.user.domain.entity.Role
import me.proton.core.user.domain.entity.User
import me.proton.core.user.domain.entity.emailSplit

/**
 * Return [User.email] or if null [User.name].
 *
 * Note: Auth endpoints infer user from email, and for sub-user [User.name] is not applicable.
 *
 * @return a non-null identifier for auth endpoints (e.g. SRP, Proof).
 */
fun User.nameNotNull() = requireNotNull(email ?: name)

/**
 * Return [User.displayName] or if null [User.name] or if null infer it from [User.emailSplit].
 *
 * @return a non-null displayName for any description (e.g. account list, internal address creation).
 */
fun User.displayNameNotNull() = requireNotNull(displayName ?: name ?: emailSplit?.username)

/**
 * @return true if the user have at least 1 key.
 */
fun User.hasKeys() = keys.isNotEmpty()

/**
 * @return true if the user have a username (not blank).
 */
fun User.hasUsername() = !name.isNullOrBlank()

/**
 * @return true if the user is private, whether the user controls their own keys or not.
 */
fun User.isPrivate() = private

/**
 * @return true if the user have the [Role.OrganizationAdmin] role.
 */
fun User.isOrganizationAdmin() = role == Role.OrganizationAdmin

/**
 * @return true if the user have the [Role.OrganizationMember] role.
 */
fun User.isOrganizationMember() = role == Role.OrganizationMember

/**
 * @return true if the user have the [Role.NoOrganization] role.
 */
fun User.isNotOrganizationUser() = role == Role.NoOrganization

/**
 * @return true if the user [isOrganizationMember] or [isOrganizationAdmin].
 */
fun User.isOrganizationUser() = isOrganizationAdmin() || isOrganizationMember()

/**
 * @return true if the user has rights to read subscription data.
 */
fun User.canReadSubscription() = role == Role.OrganizationAdmin || role == Role.NoOrganization
