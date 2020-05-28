object Cat{
    def main(args: Array[String]) {
        for (arg <- args) {
            println( io.Source.fromFile(arg).mkString)
        }
    }
}