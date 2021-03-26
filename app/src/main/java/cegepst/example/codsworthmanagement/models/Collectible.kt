package cegepst.example.codsworthmanagement.models

class Collectible(interval: Double, timestamp: Long) {
    var interval = interval
    var canCollect = false
    var lastCollectTimestamp = timestamp
}