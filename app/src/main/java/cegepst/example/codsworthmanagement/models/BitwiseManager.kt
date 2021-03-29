package cegepst.example.codsworthmanagement.models

class BitwiseManager {
    companion object {
        fun hasMrHandy(mrHandyValue: Int, bitwiseValue: Int): Boolean {
            return mrHandyValue and bitwiseValue == bitwiseValue
        }
    }
}