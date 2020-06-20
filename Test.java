有n个只包含小写字母的串s_1,s_2,..s_ns 
1
​	
 ,s 
2
​	
 ,..s 
n
​	
 ，每次给你一个只包含小写字母的串t。如果串S存在前缀S'，它的奇数位的字符与t的奇数位字符完全相同，称S为t的单匹配串，如果串S的偶数位字符与t的偶数位的字符全都相同，称S为t的双匹配串。
牛牛喜欢单数，并且觉得"双"非常的可恨。现在给你m个字符串，对于每个字符串t_it 
i
​	
 ，求s_1,s_2,...s_ns 
1
​	
 ,s 
2
​	
 ,...s 
n
​	
 中有多少个串是t的单匹配串但不是t的双匹配串。


import java.util.*;


public class Solution {
    /**
     * 单双难全
     * @param n int整型 字符串s的个数
     * @param s string字符串一维数组 n个字符串s
     * @param m int整型 字符串t的个数
     * @param t string字符串一维数组 m个字符串t
     * @return int整型一维数组
     */
    public int[] solve (int n, String[] s, int m, String[] t) {
        // write code here
        Trie trie=new Trie();
        for(String i:s){
            trie.insert(i);
        }
        Node root=trie.root;
        int[] res=new int[m];
        for(int i=0;i<m;i++){
            trie.num=0;
            trie.findSingle(t[i],0,root,new Node());
            res[i]=trie.num;
        }
        return res;
    }
}

class Trie{
    public Node root=new Node();
    
    public void insert(String s){
        Node cur=root;
        for(int i=0;i<s.length();i++){
            int index=s.charAt(i)-'a';
            if(cur.arr[index]==null){
                cur.arr[index]=new Node();
            }
            cur.arr[index].count++;
            cur=cur.arr[index];
        }
        cur.isEnd=true;
    }
    
    int num;
    public void findSingle(String s,int index,Node cur,Node pre){
        if(index==s.length()-1){
            if(s.length()%2==0){
                for(int i=0;i<26;i++){
                    if(pre.arr[i]!=null&&i!=(s.charAt(index)-'a')){
                      num+=findWord(pre.arr[i]);
                    }
                 }
            }else{
               for(int i=0;i<26;i++){
                    if(cur.arr[i]!=null){
                      num+=findWord(cur.arr[i]);
                    }
                 }
            }
            return;
        }
        if(index%2==0){
            if(cur.arr[s.charAt(index)-'a']==null){
                num=0;
                return;
            }
            findSingle(s,index+1,cur.arr[s.charAt(index)-'a'],cur);
        }else{
            for(int i=0;i<26;i++){
                 if(cur.arr[i]!=null&&i!=(s.charAt(index)-'a')){
                      findSingle(s,index+1,cur.arr[i],cur);
                 }
            }
        }
        
    }
    
    int dou=0;
    public void findDouble(String s,int index,Node cur,Node pre){
        if(index==s.length()-1){
            if(s.length()%2==1){
                for(int i=0;i<26;i++){
                    if(pre.arr[i]!=null&&i!=(s.charAt(index)-'a')){
                      dou+=findWord(pre.arr[i]);
                    }
                 }
            }else{
               for(int i=0;i<26;i++){
                    if(cur.arr[i]!=null){
                      dou+=findWord(cur.arr[i]);
                    }
                 }
            }
            return;
        }
        if(index%2==1){
            if(cur.arr[s.charAt(index)-'a']==null){
                dou=0;
                return;
            }
            findDouble(s,index+1,cur.arr[s.charAt(index)-'a'],cur);
        }else{
            for(int i=0;i<26;i++){
                 if(cur.arr[i]!=null&&i!=(s.charAt(index)-'a')){
                      findDouble(s,index+1,cur.arr[i],cur);
                 }
            }
        }
        
    }
    
    
    public int findWord(Node cur){
        int number=0;
        for(int i=0;i<26;i++){
            if(cur.arr[i]!=null){
                if(cur.arr[i].isEnd){
                    number++;
                }
                number+=findWord(cur.arr[i]);
            }
        }
        return number;
    }
}

class Node{
    public Node[] arr=new Node[26];
    public int count;
    boolean isEnd;
}