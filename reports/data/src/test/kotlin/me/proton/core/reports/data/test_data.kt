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

package me.proton.core.reports.data

import me.proton.core.domain.entity.Product
import me.proton.core.domain.entity.UserId
import me.proton.core.reports.domain.entity.BugReport
import me.proton.core.reports.domain.entity.BugReportExtra
import me.proton.core.reports.domain.entity.BugReportMeta

internal val testUserId = UserId("user-id")
internal val testBugReport = BugReport(
    title = "Title",
    description = "Bug report description",
    username = "username",
    email = "test@email"
)
internal val testBugReportMeta = BugReportMeta(
    appVersionName = "1.2.3",
    clientName = "TestApp",
    osName = "Android",
    osVersion = "100",
    Product.Mail
)
internal val testBugReportExtra = BugReportExtra(
    country = "Switzerland",
    isp = "InternetServiceProvider"
)
