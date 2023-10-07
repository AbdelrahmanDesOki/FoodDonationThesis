package com.mongodb.app.domain

import org.mongodb.kbson.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey


enum class PriorityLevel() {
    Tonight,
    Tomorrow,
    Week,
    Month
}

class Item() : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var isComplete: Boolean = false
    var summary: String = ""
    var description: String = ""
    var owner_id: String = ""
    var priority: Int = PriorityLevel.Tonight.ordinal
    var Location: String = ""

    constructor(ownerId: String = "") : this() {
        owner_id = ownerId
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Item) return false
        if (this._id != other._id) return false
        if (this.isComplete != other.isComplete) return false
        if (this.summary != other.summary) return false
        if (this.description != other.description) return false
        if (this.Location != other.Location) return false
        if (this.owner_id != other.owner_id) return false
        return true
    }

    override fun hashCode(): Int {
        var result = _id.hashCode()
        result = 31 * result + isComplete.hashCode()
        result = 31 * result + summary.hashCode()
        result = 31 * result + owner_id.hashCode()
        return result
    }
}
