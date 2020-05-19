#!/usr/bin/env sbt -Dsbt.main.class=sbt.ScriptMain

/***
scalaVersion := "2.12.1"
*/

def greet(name: String): String = s"Hello, $name!"

// Entry Point for our scsript
args.toList match{
    case List(name) => {
        val greeting = greet(name)
        println(greeting)
    }
    case _ => println("usage: HelloScript.scala <name>")
}
