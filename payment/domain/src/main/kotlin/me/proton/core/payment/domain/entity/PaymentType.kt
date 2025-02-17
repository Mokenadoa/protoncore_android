/*
 * Copyright (c) 2022 Proton Technologies AG
 * This file is part of Proton AG and ProtonCore.
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

package me.proton.core.payment.domain.entity

public sealed class PaymentType(public val type: String) {
    public data class CreditCard(val card: Card) : PaymentType("card")
    public object PayPal : PaymentType("paypal")
    public data class PaymentMethod(val paymentMethodId: String) : PaymentType("paymentmethod")
    public data class GoogleIAP(
        val productId: String,
        val purchaseToken: GooglePurchaseToken,
        val orderId: String,
        val packageName: String,
        val customerId: String
    ) : PaymentType("google")
}
