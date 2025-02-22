/*
 * Copyright (c) 2010-2022 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.openhab.habdroid.ui

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.openhab.habdroid.R
import org.openhab.habdroid.ui.WidgetAdapter.ColorMapper

class WidgetAdapterTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun testColorMappingDarkTheme() {
        val colorMapper = ColorMapper(createThemedContext(true))
        testMapping(colorMapper, "Map #ffffff", "#ffffff", -0x1)
        testMapping(colorMapper, "Must return \"null\" for invalid colors", "#fffzzz", null)
        testMapping(colorMapper, "Map white => #ffffff in dark themes", "white", -0x1)
        testMapping(colorMapper, "Map red => #ff0000 in dark themes", "red", -0x10000)
        testMapping(colorMapper, "Map yellow => #ffff00 in dark themes", "yellow", -0x100)
    }

    @Test
    fun testColorMappingBrightTheme() {
        val colorMapper = ColorMapper(createThemedContext(false))
        testMapping(colorMapper, "Map #ffffff", "#ffffff", -0x1)
        testMapping(colorMapper, "Must return \"null\" for invalid colors", "#fffzzz", null)
        testMapping(colorMapper, "Map white => #000000 in bright themes", "white", -0x1000000)
        testMapping(colorMapper, "Map red => #ff0000 in bright themes", "red", -0x10000)
        testMapping(colorMapper, "Map yellow => #fdd835 in bright themes", "yellow", -0x227cb)
    }

    private fun testMapping(mapper: ColorMapper, message: String, value: String, expected: Int?) {
        assertEquals(message, expected, mapper.mapColor(value))
    }

    private fun createThemedContext(nightModeEnabled: Boolean): Context {
        val themedContext = ContextThemeWrapper(context, R.style.openHAB_DayNight_orange)
        val nightModeBits = if (nightModeEnabled) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO
        val nightModeConfig = Configuration()
        nightModeConfig.uiMode = nightModeBits or (nightModeConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK.inv())
        themedContext.applyOverrideConfiguration(nightModeConfig)
        return themedContext
    }
}
