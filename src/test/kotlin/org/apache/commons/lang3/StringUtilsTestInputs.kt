package org.apache.commons.lang3

import io.kotest.core.Tuple4
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Assertions
import java.lang.reflect.Modifier
import java.nio.CharBuffer

class StringUtilsTestNew : FreeSpec({
    "Capitalization tests" - {
        listOf(
            "" to "",
            "x" to "X",
            StringUtilsTestInputs.FOO_CAP to StringUtilsTestInputs.FOO_CAP,
            StringUtilsTestInputs.FOO_UNCAP to StringUtilsTestInputs.FOO_CAP,
            "\u01C9" to "\u01C8",
            "cat" to "Cat",
            "cAt" to "CAt",
            "'cat'" to "'cat'",
            "" to "",
            null to null,

        ).forEach{ (input, expected) ->
            "it should capitalize '$input' to '$expected'"{
                StringUtils.capitalize(input) shouldBe expected
            }
        }
    }

    "text centering tests" - {
        listOf(
            Triple("",4,"    "),
            Triple("ab",0,"ab"),
            Triple("cd",-1,"cd"),
            Triple("ef",1,"ef"),
            Triple("ab",4," ab "),
            Triple("abcd",2,"abcd"),
            Triple("x",4," x  "),
            Triple("y",5,"  y  "),
        ).forEach { (input, size, expected) ->
            " center('$input', $size) should be '$expected'" {
                StringUtils.center(input, size) shouldBe expected
            }
        }
    }

    "text centering with padChar tests" - {
        listOf(
            Tuple4("",4,'x',"xxxx"),
            Tuple4("ab",0,' ',"ab"),
            Tuple4("cd",-1,' ',"cd"),
            Tuple4("ef",1,'x',"ef"),
            Tuple4("ab",4,'x',"xabx"),
            Tuple4("abcd",2,'x',"abcd"),
            Tuple4("x",4,' '," x  "),
            Tuple4("y",5,'x',"xxyxx"),
            Tuple4("y",5,' ',"  y  "),
        ).forEach { (input, size, padChar, expected) ->
            " center('$input', $size, '$padChar') should be '$expected'" {
                StringUtils.center(input, size, padChar) shouldBe expected
            }
        }
    }

    "text centering with pad string " - {
        listOf(
            Tuple4("",4," ","    "),
            Tuple4("ab",0," ","ab"),
            Tuple4("cd",-1," ","cd"),
            Tuple4("ef",1," ","ef"),
            Tuple4("ab",4," "," ab "),
            Tuple4("abcd",2,"x","abcd"),
            Tuple4("x",4," "," x  "),
            Tuple4("y",5,"x","xxyxx"),
            Tuple4("a",4,"yz","yayz"),
            Tuple4("a",7,"yz","yzyayzy"),
            Tuple4("abc",7,null,"  abc  "),
            Tuple4("abc",7,"","  abc  "),
        ).forEach { (input, size, padStr, expected) ->
            " center('$input', $size, '$padStr') should be '$expected'" {
                StringUtils.center(input, size, padStr) shouldBe expected
            }
        }
    }

    "test chomp" - {
        val s = StringUtilsTestInputs.FOO_UNCAP
        listOf(
            Triple(1, "${s}\r\n", s),
            Triple(2, "${s}\r", s),
            Triple(3, "${s}\n", s),
            Triple(4, "${s} \r", s+" "),
            Triple(5, s, s),
            Triple(6, "${s}\n\n", s+"\n"),
            Triple(7, "${s}\r\n\r\n", s+"\r\n"),
            Triple(8, "${s}\n${s}", "${s}\n${s}"),
            Triple(9, "${s}\n\r${s}", "${s}\n\r${s}"),
            Triple(10, "${s}\r\n${s}", "${s}\r\n${s}"),
            Triple(11, "${s}\n\r${s}", "${s}\n\r${s}"),
            Triple(12, "foo\nfoo", "foo\nfoo"),
            Triple(13, "foo\n\rfoo", "foo\n\rfoo"),
            Triple(14, "\n", ""),
            Triple(15, "\r", ""),
            Triple(16, "a", "a"),
            Triple(17, "\r\n", ""),
            Triple(18, "", ""),
            Triple(19, null, null),
            Triple(20, "foo\n\r", "foo\n"),

            ).forEach { (index, input, expected) ->
            " chomp test no. $index " {
                StringUtils.chomp(input) shouldBe expected
            }
        }
    }

    "test chomp with chomp str" - {
        listOf(
            Tuple4(1, "foobar", "bar", "foo"),
            Tuple4(2, "foobar", "baz", "foobar"),
            Tuple4(3, "foo", "foooo", "foo"),
            Tuple4(4, "foobar", "", "foobar"),
            Tuple4(5, "foobar", null, "foobar"),
            Tuple4(6, "", "foo", ""),
            Tuple4(7, "", null, ""),
            Tuple4(8, "", "", ""),
            Tuple4(9, null, "foo", null),
            Tuple4(10, null, null, null),
            Tuple4(11, null, "", null),
            Tuple4(12, "foo", "foo", ""),
            Tuple4(13, " foo", "foo", " "),
            Tuple4(14, "foo ", "foo", "foo "),
        ).forEach {
            (index, input, chompStr, expected) ->
            " chomp test no. $index " {
                StringUtils.chomp(input, chompStr) shouldBe expected
            }
        }
    }

    "test chop" - {
        listOf(
            Triple(1, "foo\r\n", "foo"),
            Triple(2, "foo\n", "foo"),
            Triple(3, "foo\r", "foo"),
            Triple(4, "foo \r", "foo "),
            Triple(5, "foo", "fo"),
            Triple(6, "foo\nfoo", "foo\nfo"),
            Triple(7, "\n", ""),
            Triple(8, "\r", ""),
            Triple(9, "\r\n", ""),
            Triple(10, null, null),
            Triple(11, "", ""),
            Triple(12, "a", "")
        ).forEach { (index, input, expected) ->
            " chop test no. $index " {
                StringUtils.chop(input) shouldBe expected
            }

        }
    }

    "test constructor" - {
        val cons = StringUtils::class.java.declaredConstructors
        cons.size shouldBe 1
        cons[0].modifiers shouldBe Modifier.PUBLIC
        StringUtils::class.java.modifiers shouldBe Modifier.PUBLIC
        StringUtils::class.java.modifiers shouldNotBe Modifier.FINAL
    }

    "test default string" - {
        StringUtils.defaultString(null) shouldBe ""
        StringUtils.defaultString("") shouldBe ""
        StringUtils.defaultString(" ") shouldBe " "
        StringUtils.defaultString("foo") shouldBe "foo"
    }

    "test default string with default" - {
        StringUtils.defaultString(null, "NULL") shouldBe "NULL"
        StringUtils.defaultString("", "NULL") shouldBe ""
        StringUtils.defaultString("abc", "NULL") shouldBe "abc"
    }

    "test default if blank with char buffers" - {
        StringUtils.defaultIfBlank(CharBuffer.wrap(""), CharBuffer.wrap("NULL")).toString() shouldBe "NULL"
        StringUtils.defaultIfBlank(CharBuffer.wrap(" "), CharBuffer.wrap("NULL")).toString() shouldBe "NULL"
        StringUtils.defaultIfBlank(CharBuffer.wrap("abc"), CharBuffer.wrap("NULL")).toString() shouldBe "abc"
        StringUtils.defaultIfBlank(CharBuffer.wrap(""), null as CharBuffer?) shouldBe null
        // Tests compatibility for the API return type
        val s:CharBuffer = StringUtils.defaultIfBlank(CharBuffer.wrap("abc"), CharBuffer.wrap("NULL"))
        s.toString() shouldBe "abc"
    }

    "test defaultIfBlank with string buffers" - {
        StringUtils.defaultIfBlank(StringBuffer(""), StringBuffer("NULL")).toString() shouldBe "NULL"
        StringUtils.defaultIfBlank(StringBuffer(" "), StringBuffer("NULL")).toString() shouldBe "NULL"
        StringUtils.defaultIfBlank(StringBuffer("abc"), StringBuffer("NULL")).toString() shouldBe "abc"
        StringUtils.defaultIfBlank(StringBuffer(""), null as StringBuffer?) shouldBe null
        // Tests compatibility for the API return type
        val s:StringBuffer = StringUtils.defaultIfBlank(StringBuffer("abc"), StringBuffer("NULL"))
        s.toString() shouldBe "abc"
    }

    
  })

 object StringUtilsTestInputs {
    private val ws = StringBuilder()
    private val nws = java.lang.StringBuilder()
    private const val hs =  160.toChar().toString()
    private val tr = StringBuilder()
    private val ntr = StringBuilder()
    private const val CHAR_MAX: Int = java.lang.Character.MAX_VALUE.code
    init {
        println("Beginning init block")
        for (i in 0..CHAR_MAX) {
            if (Character.isWhitespace(Char(i))) {
                ws.append(i.toChar().toString())
                if (i > 32) {
                    ntr.append(i.toChar().toString())
                }
            } else if (i < 40) {
                nws.append(i.toChar().toString())
            }
        }

        for (i in 0..32) {
            tr.append(i.toChar().toString())
        }
    }

    val WHITESPACE = ws.toString()
    val NON_WHITESPACE = nws.toString()
    const val HARD_SPACE = hs
    val TRIMMABLE = tr.toString()
    val ARRAY_LIST = arrayOf("foo", "bar", "baz")
    val EMPTY_ARRAY_LIST = arrayOf<String>()
    val NULL_ARRAY_LIST = arrayOf<String?>(null)
    val NULL_TO_STRING_LIST = arrayOf<Any>(object : Any() {
        override fun toString(): String {
            return null as String
        }
    })
    val MIXED_ARRAY_LIST = arrayOf<String?>(null, "", "foo")
    val MIXED_TYPE_LIST = arrayOf<Any>("foo", java.lang.Long.valueOf(2L))
    val LONG_PRIM_LIST = arrayOf<Long>(1, 2)
    val INT_PRIM_LIST = arrayOf<Int>(1, 2)
    val BYTE_PRIM_LIST = arrayOf<Byte>(1, 2)
    val SHORT_PRIM_LIST = arrayOf<Short>(1, 2)
    val CHAR_PRIM_LIST = arrayOf<Char>('1', '2')
    val FLOAT_PRIM_LIST = arrayOf<Float>(1f, 2f)
    val DOUBLE_PRIM_LIST = arrayOf<Double>(1.0, 2.0)
    val MIXED_STRING_LIST = listOf(null, "", "foo")
    val MIXED_TYPE_OBJECT_LIST = listOf("foo", java.lang.Long.valueOf(2))
    val STRING_LIST = listOf("foo", "bar", "baz")
    val EMPTY_STRING_LIST = listOf<String>()
    val NULL_STRING_LIST = listOf<String?>(null)
    const val SEPARATOR = ","
    const val SEPARATOR_CHAR = ';'
    const val COMMA_SEPARATOR_CHAR = ','
    const val TEXT_LSIT = "foo,bar,baz"
    const val TEXT_LIST_CHAR = "foo;bar;baz"
    const val TEXT_LIST_NOSEP = "foobarbaz"
    const val FOO_UNCAP = "foo"
    const val FOO_CAP = "Foo"
    const val SENTENCE_UNCAP = "foo bar baz"
    const val SENTENCE_CAP = "Foo Bar Baz"
    val EMPTY = arrayOf<Boolean>()
    val ARRAY_FALSE_FALSE = arrayOf<Boolean>(false, false)
    val ARRAY_FALSE_TRUE = arrayOf<Boolean>(false, true)
    val ARRAY_FALSE_TRUE_FALSE = arrayOf<Boolean>(false, true, false)
 }

