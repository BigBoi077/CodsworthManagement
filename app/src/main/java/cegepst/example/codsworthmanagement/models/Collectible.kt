package cegepst.example.codsworthmanagement.models

import java.util.concurrent.atomic.AtomicLong

class Collectible(interval: Double, elapsed: AtomicLong) {
    var interval = interval
    var canCollect = false
    var lastCollectTimestamp = elapsed
}