package com.arekalov.yandexshmr.presentation.aboutapp

import android.content.Context
import android.net.Uri
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction

class MyDivActionHandler(
    private val onBackClick: () -> Unit,
    private val changeLike: (Boolean) -> Unit
) : DivActionHandler() {
    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver
    ): Boolean {
        val url =
            action.url?.evaluate(resolver) ?: return super.handleAction(action, view, resolver)

        return if (url.scheme == SCHEME_SAMPLE && handleSampleAction(url, view.view.context)) {
            true
        } else {
            super.handleAction(action, view, resolver)
        }
    }

    private fun handleSampleAction(action: Uri, context: Context): Boolean {
        return when (action.host) {
            "change_like" -> {
                if (action.query == "true") {
                    changeLike(true)
                } else {
                    changeLike(false)
                }
                true
            }

            "back" -> {
                onBackClick()
                true
            }

            else -> false
        }
    }

    companion object {
        const val SCHEME_SAMPLE = "my-action"
    }
}
