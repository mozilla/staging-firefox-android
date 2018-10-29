/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package mozilla.components.service.glean

import java.util.UUID

import mozilla.components.service.glean.storages.UuidsStorageEngine
import mozilla.components.support.base.log.logger.Logger

/**
 * This implements the developer facing API for recording uuids.
 *
 * Instances of this class type are automatically generated by the parsers at build time,
 * allowing developers to record values that were previously registered in the metrics.yaml file.
 *
 * The uuid API exposes the [generateAndSet] and [set] methods.
 */
data class UuidMetricType(
    override val disabled: Boolean,
    override val category: String,
    override val lifetime: Lifetime,
    override val name: String,
    override val sendInPings: List<String>
) : CommonMetricData {

    override fun defaultStorageDestinations(): List<String> {
        return listOf("metrics")
    }

    private val logger = Logger("glean/UuidMetricType")

    /**
     * Generate a new UUID value and set it in the metric store.
     */
    fun generateAndSet(): UUID {
        val uuid = UUID.randomUUID()
        set(uuid)
        return uuid
    }

    /**
     * Explicitly set an existing UUID value
     */
    fun set(value: UUID) {
        // TODO report errors through other special metrics handled by the SDK. See bug 1499761.

        if (!shouldRecord(logger)) {
            return
        }

        // Delegate storing the event to the storage engine.
        UuidsStorageEngine.record(
                stores = sendInPings,
                category = category,
                name = name,
                value = value
        )
    }
}
