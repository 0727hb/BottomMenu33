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

    companion object{
        private val DATABADE_VER=1
        private val DATABASE_NAME="SAMPLEKOTL.db"

        //테이블
        private val TABLE_NAME="Person"
        private val COOL_ALIAS="Alias"
        private val COOL_TITLE="Title"
        private val COOL_TYPE="Type"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(
                "CREATE TABLE MEMBER(EMAIL TEST," +
                        "NAME TEXT, PASSWORD TEXT, PASSWORD_CK TEXT);"
            )

            val CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME($COOL_ALIAS TEXT PRIMARY KEY, $COOL_TITLE TEXT, $COOL_TYPE TEXT)")
            db!!.execSQL(CREATE_TABLE_QUERY);

            db!!.execSQL("CREATE TABLE CONTENT(title TEXT, " + "image BLOB, " + "category INTEGER, " + "genre TEXT, description TEXT, " + "date TEXT, " +  "reviewNum INTEGER, " + "rating REAL);")
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
            }else {

            }
        }

        return false
    }

    //CRUD

    val allPerson:List<Person>
        get(){
            val istPersons = ArrayList<Person>()
            val selectQueryHandler = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQueryHandler,null)
            if(cursor.moveToFirst())
            {
                do{
                    val alias = cursor.getString(cursor.getColumnIndex(COOL_ALIAS))
                    val title = cursor.getString(cursor.getColumnIndex(COOL_TITLE)).toString()
                    val type = cursor.getString(cursor.getColumnIndex(COOL_TYPE))
                    val person = Person(alias, title, type)

                    istPersons.add(person)
                }while (cursor.moveToNext())
            }
            db.close()
            return istPersons
        }

    fun addPerson(person: Person)
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COOL_ALIAS,person.alias)
        values.put(COOL_TITLE,person.title)
        values.put(COOL_TYPE,person.type)

        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun updatePerson(person: Person):Int
    {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COOL_ALIAS,person.alias)
        values.put(COOL_TITLE,person.title)
        values.put(COOL_TYPE,person.type)

        return db.update(TABLE_NAME,values,"$COOL_ALIAS=?", arrayOf(person.alias.toString()))
    }

    fun deletePerson(person: Person)
    {
        val db = this.writableDatabase

        db.delete(TABLE_NAME, "$COOL_ALIAS=?", arrayOf(person.alias.toString()))
        db.close()
    }


    fun NEW_Select(category: String): ArrayList<Content> {
        var db: SQLiteDatabase = readableDatabase
        val contentList: ArrayList<Content> = ArrayList<Content>()
        try {
            val cursor: Cursor = db!!.rawQuery("SELECT * FROM CONTENT WHERE category = '$category' ORDER BY date DESC", null)
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

    fun WIKI_init(title: String){
        var db: SQLiteDatabase = writableDatabase
            db!!.execSQL("INSERT INTO WIKI VALUES('$title', '', '', '', '');")
            db.close()
    }

    fun WIKI_Select(title: String): ArrayList<String>{
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

    fun updateWIKI(update_text: String, title: String, itemPosition: Int){
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
            when(flag){
                "random" -> {
                    cursor = db!!.rawQuery("SELECT * FROM CONTENT WHERE category = '$category' ORDER BY random();", null)

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
                    cursor = db!!.rawQuery("SELECT * FROM CONTENT WHERE category = '$category' ORDER BY reviewNum DESC;", null)

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
                    cursor = db!!.rawQuery("SELECT * FROM CONTENT WHERE category = '$category' ORDER BY rating DESC;", null)

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

//    fun MovieRank(flag: String): ArrayList<rankContent> {
//        var db: SQLiteDatabase = readableDatabase
//        val movieList: ArrayList<rankContent> = ArrayList<rankContent>()
//        val cursor: Cursor
//        try {
//            // Movie category only
//                when(flag){
//                    "random" -> {
//                        cursor = db!!.rawQuery("SELECT * FROM CONTENT WHERE category = 'MOVIE' ORDER BY random();", null)
//
//                        var rank = 0
//                        while (cursor.moveToNext()) {
//                            rank = rank + 1
//                            val title = cursor.getString(0)
//                            val image = cursor.getBlob(1)
//                            val description = cursor.getString(4)
//                            val content = rankContent(rank, title, image, description)
//                            movieList.add(content)
//                        }
//                    }
//                    "popularity" -> {
//                        cursor = db!!.rawQuery("SELECT * FROM CONTENT WHERE category = 'MOVIE' ORDER BY reviewNum DESC;", null)
//
//                        var rank = 0
//                        while (cursor.moveToNext()) {
//                            rank = rank + 1
//                            val title = cursor.getString(0)
//                            val image = cursor.getBlob(1)
//                            val description = cursor.getString(4)
//                            val content = rankContent(rank, title, image, description)
//                            movieList.add(content)
//                        }
//                    }
//                    "rating" -> {
//                        cursor = db!!.rawQuery("SELECT * FROM CONTENT WHERE category = 'MOVIE' ORDER BY rating DESC;", null)
//
//                        var rank = 0
//                        while (cursor.moveToNext()) {
//                            rank = rank + 1
//                            val title = cursor.getString(0)
//                            val image = cursor.getBlob(1)
//                            val description = cursor.getString(4)
//                            val content = rankContent(rank, title, image, description)
//                            movieList.add(content)
//                        }
//                    }
//                }
//        } catch (ex: Exception) {
//            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
//        }
//
//        return movieList
//    }
//
//    fun MusicRank(flag: String): ArrayList<rankContent> {
//        var db: SQLiteDatabase = readableDatabase
//        val musicList: ArrayList<rankContent> = ArrayList<rankContent>()
//        val cursor: Cursor
//        try {
//            // Music category only
//                when(flag){
//                    "random" -> {
//                        cursor = db!!.rawQuery("SELECT * FROM CONTENT WHERE category = 'MUSIC' ORDER BY random();", null)
//
//                        var rank = 0
//                        while (cursor.moveToNext()) {
//                            rank = rank + 1
//                            val title = cursor.getString(0)
//                            val image = cursor.getBlob(1)
//                            val description = cursor.getString(4)
//                            val content = rankContent(rank, title, image, description)
//                            musicList.add(content)
//                        }
//                    }
//                    "popularity" -> {
//                        cursor = db!!.rawQuery("SELECT * FROM CONTENT WHERE category = 'MUSIC' ORDER BY reviewNum DESC;", null)
//
//                        var rank = 0
//                        while (cursor.moveToNext()) {
//                            rank = rank + 1
//                            val title = cursor.getString(0)
//                            val image = cursor.getBlob(1)
//                            val description = cursor.getString(4)
//                            val content = rankContent(rank, title, image, description)
//                            musicList.add(content)
//                        }
//                    }
//                    "rating" -> {
//                        cursor = db!!.rawQuery("SELECT * FROM CONTENT WHERE category = 'MUSIC' ORDER BY rating DESC;", null)
//
//                        var rank = 0
//                        while (cursor.moveToNext()) {
//                            rank = rank + 1
//                            val title = cursor.getString(0)
//                            val image = cursor.getBlob(1)
//                            val description = cursor.getString(4)
//                            val content = rankContent(rank, title, image, description)
//                            musicList.add(content)
//                        }
//                    }
//                }
//        } catch (ex: Exception) {
//            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
//        }
//        return musicList
//    }
//
//    fun BookRank(flag: String): ArrayList<rankContent> {
//        var db: SQLiteDatabase = readableDatabase
//        val bookList: ArrayList<rankContent> = ArrayList<rankContent>()
//        val cursor: Cursor
//        try {
//            // Book category only
//                when(flag){
//                    "random" -> {
//                        cursor = db!!.rawQuery("SELECT * FROM CONTENT WHERE category = 'BOOK' ORDER BY random();", null)
//
//                        var rank = 0
//                        while (cursor.moveToNext()) {
//                            rank = rank + 1
//                            val title = cursor.getString(0)
//                            val image = cursor.getBlob(1)
//                            val description = cursor.getString(4)
//                            val content = rankContent(rank, title, image, description)
//                            bookList.add(content)
//                        }
//                    }
//                    "popularity" -> {
//                        cursor = db!!.rawQuery("SELECT * FROM CONTENT WHERE category = 'BOOK' ORDER BY reviewNum DESC;", null)
//
//                        var rank = 0
//                        while (cursor.moveToNext()) {
//                            rank = rank + 1
//                            val title = cursor.getString(0)
//                            val image = cursor.getBlob(1)
//                            val description = cursor.getString(4)
//                            val content = rankContent(rank, title, image, description)
//                            bookList.add(content)
//                        }
//                    }
//                    "rating" -> {
//                        cursor = db!!.rawQuery("SELECT * FROM CONTENT WHERE category = 'BOOK' ORDER BY rating DESC;", null)
//
//                        var rank = 0
//                        while (cursor.moveToNext()) {
//                            rank = rank + 1
//                            val title = cursor.getString(0)
//                            val image = cursor.getBlob(1)
//                            val description = cursor.getString(4)
//                            val content = rankContent(rank, title, image, description)
//                            bookList.add(content)
//                        }
//                    }
//                }
//        } catch (ex: Exception) {
//            Log.e(ContentValues.TAG, "Exception in executing insert SQL.", ex)
//        }
//        return bookList
//    }
}