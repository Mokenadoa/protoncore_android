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

package me.proton.core.challenge.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import androidx.viewbinding.ViewBinding
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import me.proton.core.challenge.domain.ChallengeManager
import me.proton.core.challenge.presentation.databinding.ProtonMetadataInputBinding
import me.proton.core.presentation.ui.view.ProtonInput
import javax.inject.Inject

@AndroidEntryPoint
public class ProtonMetadataInput : ProtonInput, ProtonCopyPasteEditText.CopyPasteListener {

    @Inject
    public lateinit var challengeManager: ChallengeManager

    private var inputMetadataBinding: ViewBinding? = null

    override val binding: ViewBinding
        get() {
            if (inputMetadataBinding == null) {
                inputMetadataBinding = ProtonMetadataInputBinding.inflate(LayoutInflater.from(context), this)
            }
            return inputMetadataBinding!!
        }

    override val input: ProtonCopyPasteEditText
        get() = (binding as ProtonMetadataInputBinding).input

    override val inputLayout: TextInputLayout
        get() = (binding as ProtonMetadataInputBinding).inputLayout

    override val label: TextView
        get() = (binding as ProtonMetadataInputBinding).label

    private var lastFocusOn: Long = 0
    private var focusList = mutableListOf<Int>()

    private lateinit var flow: String

    private lateinit var frame: String

    private var clicksCounter: Int = 0

    private val copies: List<String>
        get() = input.copyList

    private val pastes: List<String>
        get() = input.pasteList

    private val keys: MutableList<String> = mutableListOf()

    public constructor(context: Context) : super(context) {
        init(context)
    }

    public constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    public constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet? = null) {
        context.withStyledAttributes(attrs, R.styleable.ChallengeInput) {
            flow = getString(R.styleable.ChallengeInput_challengeFlow) ?: ""
            frame = getString(R.styleable.ChallengeInput_challengeFrame) ?: ""
        }

        enableMetrics()
        input.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                val char = when (keyCode) {
                    KeyEvent.KEYCODE_DEL -> BACKSPACE
                    KeyEvent.KEYCODE_TAB -> TAB
                    KeyEvent.KEYCODE_SHIFT_LEFT,
                    KeyEvent.KEYCODE_SHIFT_RIGHT -> SHIFT
                    KeyEvent.KEYCODE_CAPS_LOCK -> CAPS
                    KeyEvent.KEYCODE_DPAD_LEFT -> ARROW_LEFT
                    KeyEvent.KEYCODE_DPAD_RIGHT -> ARROW_RIGHT
                    KeyEvent.KEYCODE_DPAD_UP -> ARROW_UP
                    KeyEvent.KEYCODE_DPAD_DOWN -> ARROW_DOWN
                    KeyEvent.KEYCODE_COPY -> COPY
                    KeyEvent.KEYCODE_PASTE -> PASTE
                    else -> null
                }
                if (char != null) {
                    keys.add(char)
                }
            }
            false
        }

        addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // noop
            }

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                val range = IntRange(start, start + count - 1)
                val newText = text.substring(range)
                val rangeCount = range.count()
                if (rangeCount > 1) {
                    if (newText == DOT_COM) {
                        // .com is an exception
                        keys += newText.toCharArray().map { it.toString() }
                    } else {
                        keys += newText.toCharArray()[rangeCount - 1].toString()
                    }
                    return
                }
                if (text.isNotEmpty()) {
                    keys += newText.toCharArray().map { it.toString() }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // noop
            }

        })
        input.setCopyPasteListener(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun enableMetrics() {
        input.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                clicksCounter++
            }
            super.onTouchEvent(event)
        }
    }

    public suspend fun flush() {
        challengeManager.addOrUpdateFrameToFlow(
            flow = flow,
            challengeFrame = frame,
            focusTime = focusList,
            clicks = clicksCounter,
            copies = copies,
            pastes = pastes,
            keys = keys
        )
    }

    override fun onCopyHappened() {
        keys.add(COPY)
    }

    override fun onPasteHappened() {
        keys.removeLastOrNull()
        keys.add(PASTE)
    }

    override fun onFocusChanged(focused: Boolean) {
        lastFocusOn = if (focused) {
            System.currentTimeMillis()
        } else {
            val lastFocusTime = (System.currentTimeMillis() - lastFocusOn) / 1000
            focusList.add(lastFocusTime.toInt())
            0
        }
    }

    private companion object {
        private const val BACKSPACE = "Backspace"
        private const val COPY = "Copy"
        private const val PASTE = "Paste"
        private const val TAB = "Tab"
        private const val CAPS = "Caps"
        private const val SHIFT = "Shift"
        private const val ARROW_LEFT = "ArrowLeft"
        private const val ARROW_RIGHT = "ArrowRight"
        private const val ARROW_UP = "ArrowUp"
        private const val ARROW_DOWN = "ArrowDOWN"
        private const val DOT_COM = ".com"
    }
}
