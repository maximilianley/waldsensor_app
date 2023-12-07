package com.example.tent6

import android.content.Context
import java.io.File
import java.io.FileWriter
import java.io.BufferedReader
import java.io.FileReader
import java.io.BufferedWriter
import java.io.IOException

class MyFile(filename:String, context:Context, create:Boolean = true,
             private val initialContent: String? = null
) {

    val file = File(context.filesDir, filename)

    init {
        if (create) {
            openFile()
        }
    }

    private fun openFile(){
        if (!file.exists()){
            println("File non esiste e deve essere creato.")
            try {
                file.createNewFile()
                println("created ${file.canonicalPath}")
                if(initialContent!=null){
                    clearFile()
                    appendLine(initialContent) // method automatically appends a "\n" at the end
                }
            }catch (e: IOException){
                println(e.toString())
            }
        }
    }

    fun create(){
        openFile()
    }

    fun exists():Boolean{
        return file.exists()
    }

    fun readLine(index:Int):String{
        var line: String? = null
        try {
            openFile()
            BufferedReader(FileReader(file)).use { reader ->
                var currentLineIndex = 0
                while (currentLineIndex < index) {
                    line = reader.readLine()
                    if (line == null)
                        break // Reached the end of the file before reaching the desired index
                    currentLineIndex++
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            line=null
        }

        if (line==null){
            line=""
        }

        return line!!
    }

    /*fun getAllLines():Array<String>{
        var lines = mutableListOf<String>()
        try {
            openFile()
            BufferedReader(FileReader(file)).use { reader ->
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    lines.add(line!!)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            lines = mutableListOf<String>() // empty list
        }
        return lines.toTypedArray()
    }*/

    fun getAllLines():List<String>{
        try {
            openFile()
        }catch (e:IOException){
            e.printStackTrace()
        }
        return file.readLines()
    }

    fun getAllLinesMutable():MutableList<String>{
        return getAllLines().toMutableList()
    }

    fun orderContentsAlphabetically(){
        try{
            openFile()
            val lines = file.readLines().sorted()

            file.writeText("") // Clear the contents of the file

            for (line in lines) {
                file.appendText("$line\n")
            }
        }catch (e:IOException){
            e.printStackTrace()
        }
    }

    fun clearFile():Boolean{
        var cleared = true
        try {
            openFile()
            file.writeText("")
        }catch (e:IOException){
            cleared = false
            e.printStackTrace()
        }
        return cleared
    }

    fun appendLine(string:String){
        try {
            openFile()
            file.appendText("$string\n")
        }catch (e:IOException){
            e.printStackTrace()
        }
    }

    fun clearAndWriteNewLine(line:String){
        // also write a check that "line" doesn't have a "\n"-linebreak character
        clearFile()
        appendLine(line)
    }

    fun getLineIndexByContent(content:String):Int{
        return 0
    }

    fun deleteLineByContent(content:String){
        try {
            var contentDeleted = false
            openFile()
            val tempFile = File("${file.absolutePath}.tmp")
            BufferedReader(FileReader(file)).use { reader ->
                BufferedWriter(FileWriter(tempFile)).use { writer ->
                    var currentLine: String?

                    while (reader.readLine().also { currentLine = it } != null){
                        if (currentLine != content || contentDeleted){ // need "|| contentDeleted" in order to delete only the first occurence of 'content'
                            writer.write(currentLine)
                            writer.newLine()
                        }else{
                            contentDeleted = true
                        }
                    }
                }
            }
            tempFile.renameTo(file)

            if (contentDeleted) println("Deleted line with ${content}.")
            else println("Didn't find and didn't delete ${content}.")

        }catch(e:IOException){
            e.printStackTrace()
        }
    }

    fun deleteLineByIndex(index: Int){
        try {
            openFile()
            val tempFile = File("${file.absolutePath}.tmp")
            BufferedReader(FileReader(file)).use { reader ->
                BufferedWriter(FileWriter(tempFile)).use { writer ->
                    var currentLine: String?
                    var currentLineNumber = 0

                    while (reader.readLine().also { currentLine = it } != null) {
                        if (currentLineNumber != index) {
                            writer.write(currentLine)
                            writer.newLine()
                        }
                        currentLineNumber++
                    }
                }
            }
            tempFile.renameTo(file)
        }
        catch(e:IOException){
            e.printStackTrace()
        }
    }

}