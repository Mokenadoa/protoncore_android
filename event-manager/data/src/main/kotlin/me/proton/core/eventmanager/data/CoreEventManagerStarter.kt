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

package me.proton.core.eventmanager.data

import androidx.lifecycle.Lifecycle
import me.proton.core.accountmanager.domain.AccountManager
import me.proton.core.accountmanager.presentation.observe
import me.proton.core.accountmanager.presentation.onAccountReady
import me.proton.core.eventmanager.domain.EventManagerConfig
import me.proton.core.eventmanager.domain.EventManagerProvider
import me.proton.core.presentation.app.AppLifecycleProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoreEventManagerStarter @Inject constructor(
    private val appLifecycleProvider: AppLifecycleProvider,
    private val accountManager: AccountManager,
    private val eventManagerProvider: EventManagerProvider
) {

    private fun startReadyAccount() {
        accountManager.observe(appLifecycleProvider.lifecycle, minActiveState = Lifecycle.State.CREATED)
            .onAccountReady { eventManagerProvider.get(EventManagerConfig.Core(it.userId)).start() }
    }

    fun start() {
        startReadyAccount()
    }
}
