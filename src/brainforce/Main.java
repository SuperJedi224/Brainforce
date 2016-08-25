package brainforce;

import lang.*;
import lang.exceptions.IllegalInvocationException;

import java.util.*;

public class Main {
	static class BrainforceException extends IllegalInvocationException{

		public BrainforceException(String name) {
			super(name);
		}
		
	}
	static boolean autoReset=true;
	static boolean output=true;
	static short[]data=new short[10000];
	static Queue<Character>queue=new LinkedList<>();
	static int pointer=0;
	static FString run(String code){
		code=code+" ";
		StringBuilder s=new StringBuilder();
		char[]prog=code.toCharArray();
		if(autoReset){
			data=new short[10000];
			pointer=0;
		}
		for(int i=0;i<code.length();i++){
			if(prog[i]=='.'){
				s.append((char)data[pointer]);
				if(output){System.out.print((char)data[pointer]);}
			}
			if(prog[i]==','){
				if(queue.isEmpty()){
					data[pointer]=0;
				}else data[pointer]=(short)(char)queue.poll();
			}
			if(prog[i]=='>'){
				pointer++;
				if(pointer>data.length)data=Arrays.copyOf(data,(int)(1.4*data.length));
			}
			if(prog[i]=='<'){
				pointer--;
				if(pointer<0)throw new BrainforceException("Attempted to leave left end of tape.");
			}
			if(prog[i]=='+'){
				data[pointer]++;
			}
			if(prog[i]=='-'){
				data[pointer]--;
			}
			if(prog[i]=='['&&data[pointer]==0){
				int k=1;
				i++;
				for(;k>0;i++){
					if(i>prog.length)throw new BrainforceException("Could not find matching closing bracket.");
					if(prog[i]=='[')k++;
					if(prog[i]==']')k--;
				}
			}
			if(prog[i]==']'&&data[pointer]!=0){
				int k=1;
				i--;
				for(;k>0;i--){
					if(i<0)throw new BrainforceException("Could not find matching opening bracket.");
					if(prog[i]==']')k++;
					if(prog[i]=='[')k--;
				}
			}
		}
		if(autoReset){
			queue.clear();
		}
		return new FString(s.toString());
	}
	public static void load(Module m){
		autoReset=true;
		m.set("._invoke",new Function(a->{
			return run(a);
		}));
		m.set("exec",new Function(a->{
			return run(ForceLang.stringify(ForceLang.parse(a)));
		}));
		m.set("setAutoResetEnabled",new Function(a->{
			autoReset=FBool.valueOf(ForceLang.parse(a)).isTruthy();
			return null;
		}));
		m.set("setOutputEnabled",new Function(a->{
			output=FBool.valueOf(ForceLang.parse(a)).isTruthy();
			return null;
		}));
		m.set("offerInput",new Function(a->{
			char[]ch=ForceLang.stringify(ForceLang.parse(a)).toCharArray();
			for(char c:ch)queue.add(c);
			return new FNum(queue.size());
		}));
		
	}
}