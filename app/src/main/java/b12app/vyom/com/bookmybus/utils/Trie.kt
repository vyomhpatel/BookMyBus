package b12app.vyom.com.bookmybus.utils

class Trie<T> {
    class Node<T>{
        var childs:Array<Node<T>?>
        var isWord: Boolean
        var prefix_num: Int
        var bean:T ?= null
        init {
            childs = arrayOfNulls<Node<T>>(256)
            isWord=false
            prefix_num=0
        }
    }

    var bean:T?=null
    val root = Node<T>()

    fun insert(bean: T, insertString:String) {
        this.bean=bean
        insert(root, insertString)
    }

    fun insert(root: Node<T>, words: String) {
        var root = root
        var words = words
        words = words.toLowerCase()////转化为小写
        val chrs = words.toCharArray()

        var i = 0
        val length = chrs.size
        while (i < length) {
            ///用相对于a字母的值作为下标索引，也隐式地记录了该字母的值
            val index = chrs[i] - ' '
            if (root.childs.get(index) != null) {
                ////已经存在了，该子节点prefix_num++
                root.childs.get(index)!!.prefix_num++
            } else {
                ///如果不存在
                root.childs[index] = Node()
                root.childs[index]!!.prefix_num++
            }

            ///如果到了字串结尾，则做标记
            if (i == length - 1) {
                root.childs[index]!!.isWord = true
                root.childs[index]!!.bean=bean

            }
            ///root指向子节点，继续处理
            root = root.childs[index]!!
            i++
        }
    }

    fun preTraversal(root: Node<T>, prefix: String): ArrayList<T> {
        var result = ArrayList<T>()
        if (root != null) {
            if (root.isWord == true) {
                if(root.bean!=null)
                     result.add(root.bean!!)
            }
            var i = 0
            while (i < 256) {
                if (root.childs.get(i) != null) {
                    val ch = (i + ' '.toInt()).toChar()
                    ////递归调用前序遍历
                    val tempStr = prefix + ch
                    result.addAll(preTraversal(root.childs[i]!!, tempStr))
                }
                i++
            }
        }
        return result
    }

    fun search(root: Node<T>, word: String): Boolean {
        var root = root
        val chs = word.toLowerCase().toCharArray()
        var i = 0
        val length = chs.size
        while (i < length) {
            val index = chs[i] - ' '
            if (root.childs.get(index) == null) {
                ///如果不存在，则查找失败
                return false
            }
            root = root.childs[index]!!
            i++
        }

        return true
    }

    fun getWordsForPrefix(root: Node<T>, prefix: String): ArrayList<T>? {
        var root = root
        val chrs = prefix.toLowerCase().toCharArray()
        var i = 0
        val length = chrs.size
        while (i < length) {
            val index = chrs[i] - ' '
            if (root.childs.get(index) == null) {
                return null
            }
            root = root.childs[index]!!
            i++
        }
        ///结果包括该前缀本身
        ///此处利用之前的前序搜索方法进行搜索
        return preTraversal(root, prefix)
    }

}