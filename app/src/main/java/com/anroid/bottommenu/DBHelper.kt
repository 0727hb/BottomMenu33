package com.anroid.bottommenu

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.*

@Suppress("DEPRECATION")
class DBHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) :
    SQLiteOpenHelper(context, name, factory, version) {

    companion object {
        private val DATABADE_VER = 1
        private val DATABASE_NAME = "SAMPLEKOTL.db"

        //테이블
        private val TABLE_NAME = "Person"
        private val COOL_ALIAS = "Alias"
        private val COOL_TITLE = "Title"
        private val COOL_TYPE = "Type"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(
                "CREATE TABLE MEMBER(EMAIL TEST," +
                        "NAME TEXT, PASSWORD TEXT, PASSWORD_CK TEXT);"
            )

            db!!.execSQL("CREATE TABLE $TABLE_NAME($COOL_ALIAS TEXT PRIMARY KEY, $COOL_TITLE TEXT, $COOL_TYPE TEXT)");
            db!!.execSQL("CREATE TABLE REVIEW(alias TEXT," + " title TEXT," + " review TEXT," + " description TEXT," + " rating REAL, " + " emotion TEXT, " + " recommend TEXT);")
            db!!.execSQL("CREATE TABLE CONTENT(title TEXT, " + "image BLOB, " + "category INTEGER, " + "genre TEXT, description TEXT, " + "date TEXT, " + "reviewNum INTEGER, " + "rating REAL);")
            db!!.execSQL("CREATE TABLE WIKI(image BLOB," + "title CHAR(20));")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    fun insert(
        email: String, name: String, password: String, password_ck: String
    ) {
        var db: SQLiteDatabase = writableDatabase

        db.execSQL(
            "INSERT INTO MEMBER VALUES('" + email + "'" + ", '" + name + "'" + ", '" + password + "'" + ", '" + password_ck + "');"

        )
        db.close()
    }

    fun update(
        name: String, password: String, password_ck: String, email: String
    ) {
        var db: SQLiteDatabase = writableDatabase

        db.execSQL(
            "UPDATE MEMBER SET PASSWORD = " + "'" + password + "'" + ", PASSWORD_OK = '" + password_ck + "'"
                    + ", EMAIL = '" + email + "'" +
                    "WHERE NAME = '" + name + "';"
        )

        db.close()
    }

    fun getResult(): String {
        var db: SQLiteDatabase = readableDatabase
        var result: String = ""

        var cursor: Cursor = db.rawQuery("SELECT * FROM MEMBER", null)
        while (cursor.moveToNext()) {
            result += (cursor.getString(0)
                    + " : "
                    + cursor.getString(1)
                    + " : "
                    + cursor.getString(2)
                    + " : "
                    + cursor.getString(3)
                    + " : "
                    + cursor.getString(4)
                    + "\n")

        }

        return result
    }

    fun getResult1(ID: String, PASSWORD: String): Boolean {
        var db: SQLiteDatabase = readableDatabase
        var result: String = ""

        var cursor: Cursor = db.rawQuery("SELECT ID, PASSWORD FROM MEMBER", null)
        while (cursor.moveToNext()) {
            result = (cursor.getString(0))
            if (result.equals(ID)) {
                if (cursor.getString(1).equals(PASSWORD)) {
                    return true
                    break
                } else {
                    return false
                }
            } else {

            }
        }

        return false
    }

    fun selectReivew(): ArrayList<Review> {
        var db: SQLiteDatabase = readableDatabase
        val reviewList: ArrayList<Review> = ArrayList<Review>()
        try {
            val cursor: Cursor = db!!.rawQuery("SELECT * FROM REVIEW;", null)
            while (cursor.moveToNext()) {
                val alias = cursor.getString(cursor.getColumnIndex("alias"))
                val title = cursor.getString(cursor.getColumnIndex("title"))
                val review = cursor.getString(cursor.getColumnIndex("review"))
                val description = cursor.getString(cursor.getColumnIndex("description"))
                val rating = cursor.getFloat(cursor.getColumnIndex("rating"))
                val emotion = cursor.getString(cursor.getColumnIndex("emotion"))
                val recommend = cursor.getString(cursor.getColumnIndex("recommend"))
                val review_content =
                    Review(alias, title, review, description, rating, emotion, recommend)
                reviewList.add(review_content)
            }
        } catch (ex: Exception) {
            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
        }
        db.close()
        return reviewList
    }

    fun addReview(
        alias: String,
        title: String,
        review: String,
        description: String,
        rating: Float,
        emotion: String,
        recommend: String
    ) {
        var db: SQLiteDatabase = writableDatabase
        db!!.execSQL("INSERT INTO REVIEW VALUES('$alias', '$title', '$review', '$description', '$rating', '$emotion', '$recommend');")
        db.close()
    }

    fun updateReview(
        alias: String,
        title: String,
        review: String,
        description: String,
        rating: Float,
        emotion: String,
        recommend: String
    ) {
        var db: SQLiteDatabase = writableDatabase
        db!!.execSQL("INSERT INTO REVIEW SET title = '$title' WHERE alias = '$alias';")
        db!!.execSQL("INSERT INTO REVIEW SET review = '$review' WHERE alias = '$alias';")
        db!!.execSQL("INSERT INTO REVIEW SET description = '$description' WHERE alias = '$alias';")
        db!!.execSQL("INSERT INTO REVIEW SET rating = '$rating' WHERE alias = '$alias';")
        db!!.execSQL("INSERT INTO REVIEW SET emotion = '$emotion' WHERE alias = '$alias';")
        db!!.execSQL("INSERT INTO REVIEW SET recommend = '$recommend' WHERE alias = '$alias';")
        db.close()
    }

    fun deleteReview(alias: String, title: String) {
        var db: SQLiteDatabase = writableDatabase
        db!!.execSQL("DELETE FROM REVIEW WHERE alias = '$alias' AND title = '$title';")
        db.close()
    }

    fun NEW_Select(category: String): ArrayList<Content> {
        var db: SQLiteDatabase = readableDatabase
        val contentList: ArrayList<Content> = ArrayList<Content>()
        try {
            val cursor: Cursor = db!!.rawQuery(
                "SELECT * FROM CONTENT WHERE category = '$category' ORDER BY date DESC",
                null
            )
            var count = 0
            while (cursor.moveToNext() && count <= 10) {
                count += 1
                val image = cursor.getBlob(1)
                val title = cursor.getString(0)
                val content = Content(image, title)
                contentList.add(content)
            }
        } catch (ex: Exception) {
            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
        }
        db.close()
        return contentList
    }

    fun select_all(): ArrayList<Content> {
        var db: SQLiteDatabase = readableDatabase
        val contentList: ArrayList<Content> = ArrayList<Content>()
        try {
            val cursor: Cursor = db!!.rawQuery("SELECT * FROM CONTENT;", null)
            var count = 0
            while (cursor.moveToNext() && count <= 10) {
                count += 1
                val image = cursor.getBlob(1)
                val title = cursor.getString(0)
                val content = Content(image, title)
                contentList.add(content)
            }
        } catch (ex: Exception) {
            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
        }
        db.close()
        return contentList
    }

    fun WIKI_Insert(title: String) {
        var db: SQLiteDatabase = writableDatabase
        db!!.execSQL("INSERT INTO WIKI VALUES('$title', '', '', '', '');")
        db.close()
    }

    fun WIKI_Select(title: String): ArrayList<String> {
        var db: SQLiteDatabase = readableDatabase
        var content: String
        val forumList: ArrayList<String> = ArrayList<String>()
        try {
            val cursor: Cursor = db!!.rawQuery("SELECT * FROM WIKI WHERE title = '$title'", null)
            var position = 1
            while (position <= 4) {
                cursor.moveToFirst()
                content = cursor.getString(position)
                forumList.add(content)
                position = position + 1
            }
        } catch (ex: Exception) {
            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
        }
        db.close()
        return forumList
    }

    fun updateWIKI(update_text: String, title: String, itemPosition: Int) {
        var db: SQLiteDatabase = writableDatabase
        when (itemPosition) {
            0 -> db!!.execSQL("UPDATE WIKI SET content_1 = '$update_text' WHERE title = '$title';")
            1 -> db!!.execSQL("UPDATE WIKI SET content_2 = '$update_text' WHERE title = '$title';")
            2 -> db!!.execSQL("UPDATE WIKI SET content_3 = '$update_text' WHERE title = '$title';")
            3 -> db!!.execSQL("UPDATE WIKI SET content_4 = '$update_text' WHERE title = '$title';")
            else -> false
        }
        db.close()
    }

    fun ContentRank(flag: String, category: String): ArrayList<rankContent> {
        var db: SQLiteDatabase = readableDatabase
        val contentList: ArrayList<rankContent> = ArrayList<rankContent>()
        val cursor: Cursor
        try {
            // Book category only
            when (flag) {
                "random" -> {
                    cursor = db!!.rawQuery(
                        "SELECT * FROM CONTENT WHERE category = '$category' ORDER BY random();",
                        null
                    )

                    var rank = 0
                    while (cursor.moveToNext()) {
                        rank = rank + 1
                        val title = cursor.getString(0)
                        val image = cursor.getBlob(1)
                        val description = cursor.getString(4)
                        val content = rankContent(rank, title, image, description)
                        contentList.add(content)
                    }
                }
                "popularity" -> {
                    cursor = db!!.rawQuery(
                        "SELECT * FROM CONTENT WHERE category = '$category' ORDER BY reviewNum DESC;",
                        null
                    )

                    var rank = 0
                    while (cursor.moveToNext()) {
                        rank = rank + 1
                        val title = cursor.getString(0)
                        val image = cursor.getBlob(1)
                        val description = cursor.getString(4)
                        val content = rankContent(rank, title, image, description)
                        contentList.add(content)
                    }
                }
                "rating" -> {
                    cursor = db!!.rawQuery(
                        "SELECT * FROM CONTENT WHERE category = '$category' ORDER BY rating DESC;",
                        null
                    )

                    var rank = 0
                    while (cursor.moveToNext()) {
                        rank = rank + 1
                        val title = cursor.getString(0)
                        val image = cursor.getBlob(1)
                        val description = cursor.getString(4)
                        val content = rankContent(rank, title, image, description)
                        contentList.add(content)
                    }
                }
            }
        } catch (ex: Exception) {
            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
        }
        db.close()
        return contentList
    }
}