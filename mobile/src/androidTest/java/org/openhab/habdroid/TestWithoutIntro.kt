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

package org.openhab.habdroid

import android.content.Context
import androidx.core.content.edit
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.openhab.habdroid.util.PrefKeys
import org.openhab.habdroid.util.getPrefs

abstract class TestWithoutIntro : ProgressbarAwareTest() {
    @Before
    override fun setup() {
        ApplicationProvider.getApplicationContext<Context>().getPrefs().edit {
            putString(PrefKeys.SITEMAP_NAME, "")
            if (preselectSitemap()) {
                putString(PrefKeys.SITEMAP_NAME, "demo")
                putString(PrefKeys.SITEMAP_LABEL, "Main Menu")
            }

            putBoolean(PrefKeys.DEMO_MODE, true)
            putBoolean(PrefKeys.FIRST_START, false).commit()
        }

        super.setup()
        setupRegisterIdlingResources()
    }

    protected open fun preselectSitemap(): Boolean {
        return false
    }
}
