/*
 * Copyright (c) 2020 Proton Technologies AG
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

package me.proton.core.accountmanager.data

import me.proton.core.accountmanager.domain.SessionManager
import me.proton.core.auth.domain.repository.AuthRepository
import me.proton.core.network.domain.session.SessionId
import me.proton.core.network.domain.session.SessionListener
import me.proton.core.network.domain.session.SessionProvider
import javax.inject.Inject

class SessionManagerImpl @Inject constructor(
    private val sessionProvider: SessionProvider,
    private val sessionListener: SessionListener,
    private val authRepository: AuthRepository
) : SessionManager,
    SessionProvider by sessionProvider,
    SessionListener by sessionListener {

    override suspend fun refreshScopes(sessionId: SessionId): List<String> =
        authRepository.getScopes(sessionId).also { scopes ->
            sessionListener.onSessionScopesRefreshed(sessionId, scopes)
        }
}
