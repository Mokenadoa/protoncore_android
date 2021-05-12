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

package me.proton.core.humanverification.domain

import me.proton.core.network.domain.session.ClientId

interface HumanVerificationWorkflowHandler {

    /**
     * Handle HumanVerification success.
     *
     * Note: TokenType and tokenCode must be part of the next API calls.
     */
    suspend fun handleHumanVerificationSuccess(clientId: ClientId, tokenType: String, tokenCode: String)

    /**
     * Handle HumanVerification failure.
     */
    suspend fun handleHumanVerificationFailed(clientId: ClientId)

    /**
     * Handle HumanVerification canceled by the user.
     */
    suspend fun handleHumanVerificationCanceled(clientId: ClientId)
}
