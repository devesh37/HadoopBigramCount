package com.hadoopproject.hadoopbigramcount;


/**
 * The Mapper class for Bigram counting - given a line, it outputs (bigram, 1) for each
 * of the bigrams in the line
 */

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class Map extends Mapper<LongWritable,Text,TextPair,IntWritable> {
    private  Text lastWord = null;
    private  Text currentWord = new Text();


    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
    {
        String line = value.toString();
        line = line.replaceAll("[^a-zA-Z0-9]+"," ").toLowerCase();
        StringTokenizer words = new StringTokenizer(line);
        String lastWord=null;
        while (words.hasMoreTokens()) {
        	String currentWord=words.nextToken();
        	if(lastWord==null)
        		lastWord=currentWord;
        	else
        	{
        		TextPair textPair=new TextPair(lastWord,currentWord);
        		context.write(textPair, new IntWritable(1));
        		lastWord=currentWord;
        		
        	}
        }
        
    }
}
