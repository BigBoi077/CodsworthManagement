package cegepst.example.codsworthmanagement.models

class Collectible(interval: Double, bitwise: Boolean) {
    var interval = interval
    var canCollect = false
    var isMrHandyActivated = bitwise
    var lastCollectTimestamp = 0L
    var progress = 0
}