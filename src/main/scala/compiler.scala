import scala.scalajs.js.JSApp
import scala.scalajs.js
import org.scalajs.jquery.jQuery

object Compiler {
  def compile(input: String): String = {
    if (input == "") return input;
    val program = "(function brainfuck() {\nvar ptr = 0;\nvar prog = [0];\n"+
    "function setByte() { if (prog[ptr] == null) prog[ptr] = 0; }\n" +
    "function incPtr() { ptr++; setByte(); }\nfunction decPtr() { ptr--; setByte(); }\n" +
    "function readInput() { var input = prompt('input'); if (input == null) return; " +
    "else prog[ptr] = input.charCodeAt(0); }\n" +
    "function printOutput() { alert(String.fromCharCode(prog[ptr])); }\n";
    program + input.toList.collect {
	  case '+' => "prog[ptr]++;"
	  case '-' => "prog[ptr]--;"
	  case '>' => "incPtr();"
	  case '<' => "decPtr();"
	  case '[' => "while(prog[ptr] != 0) {"
	  case ']' => "}"
	  case ',' => "readInput();"
	  case '.' => "printOutput();"
    }.mkString("\n") + "})();"
  }
}

object Bf2js extends JSApp {
  def compile(): Unit = {
    val source: String = jQuery("#source").value().toString();
    val program = Compiler.compile(source);
    jQuery("#target").value(program);
  }

  def run(): Unit = {
    js.eval(jQuery("#target").value().toString());
  }

  def hello(): Unit = {
    jQuery("#source").value("++++++++[>++++[>++>+++>+++>+<<<<-]" +
			    ">+>+>->>+[<]<-]>>.>---.+++++++..+++" + 
			    ".>>.<-.<.+++.------.--------.>>+.>++.");
  }

  def clear(): Unit = {
    jQuery("#source").value("");
    jQuery("#target").value("");
  }

  def addButton(text: String, func: () => Unit): Unit = {
    val id = text.replace(' ', '_');
    jQuery("body").append(s"<button id='$id'>$text</button>");
    jQuery(s"#$id").click(func);
  }

  def main(): Unit = {
    jQuery("body").append("<p>Brainfuck to JavaScript compiler</p>")
      .append("<textarea rows=20 cols=80 id='source' placeholder='source'/>")
      .append("<textarea rows=20 cols=80 id='target' placeholder='target'/>");
    List(("Hello world", hello _), ("Compile", compile _), ("Run", run _), ("Clear", clear _)).map{
      case (text, func) => addButton(text, func)
    }
  }
}
