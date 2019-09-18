package anagram.rest.ydeer.service;

import org.springframework.stereotype.Service;

@Service("AnagramService")
public class AnagramService {
	public String RetrunAnagram(Object input)
	{
		// 파라미터를 주지 않았을 경우.
		if (input == null)
		{
			return "에러: 입력값으로 문자열을 주시지 않았습니다.";
		}
		String rest;
		try
		{
			rest = (String)input;
		}
		catch (ClassCastException e)
		{
			return "에러: 입력값으로 문자열을 주시지 않았습니다.";
		}
		// 입력 문자열이 빈 문자열이라면 처리할 필요가 없으므로 그대로 반환.
		if (rest.length() == 0)
		{
			return rest;
		}
		/*
		 * rest: 입력 문자열 중 아직 처리되지 않은 남은 문자들.
		 * result: 처리된 문자열로 최종적으로 반환될 문자열.
		 */
		StringBuilder result = new StringBuilder();
		while (rest.length() > 0)
		{
			int index = 0;
			/*
			 * 아직 처리되지 않은 남은 문자열에서, 공백 문자는 그대로 바로바로 result에 넣어버린다.
			 */
			while (rest.charAt(index) == ' ')
			{
				result.append(' ');
				++index;
				// 공교롭게도 남은 문자가 없이 이대로 끝이라면, 바로 result를 반환.
				if (rest.length() <= index)
				{
					return result.toString();
				}
			} 
			StringBuilder now = new StringBuilder();
			while (index < rest.length())
			{
				/*
				 * 한글만 문자열 순서를 섞어주기 위해 한글검사를 한 후, now에 쌓아준다.
				 */
				if (rest.charAt(index) >= '가' && rest.charAt(index) <= '힣')
				{
					now.append(rest.charAt(index++));
				}
				else
				{
					break;
				}
			}
			/*
			 * now에 뭔가 쌓였다면 연속된 한글이 들어온 것
			 */
			if (now.length() > 0)
			{
				/* 한글이 4글자 이상이어야 한다.
				 * 앞 글자와 끝 글자만 두고 그 사이만 순서를 섞으려면 그 사이의 문자열 크기가 2 이상이어야 함. (한 글자는 섞을 수 없으니)
				 */
				if (now.length() > 3)
				{
					/*
					 * 첫 글자를 먼저 맨 앞에 붙여주고,
					 * 마지막에는 끝 글자를 맨 끝에 붙여준다.
					 * 그 사이 글자는 짝수 nowindex라면 자신보다 앞의 글자를 넣고,
					 * 홀수 nowindex라면 자신보다 뒤의 글자를 넣는다.
					 * 단, 홀수 중 자신보다 뒤의 글자가 없을 경우 (ex. "가나다라마" 중 "나다라"에서 '라'. nowindex가 3이 될 것.)
					 * 즉, 홀수이면서 "끝 글자 앞의 글자"일 경우에는 어쩔 수 없으니 그냥 자신을 넣는다.
					 * 짝수라면 항상 자신보다 앞의 글자는 존재하므로 고려하지 않아도 된다.
					 * 
					 * 결과적으로 이 방식은 문자열에서 연속된 두 글자끼리만 서로 바꾸는 아주 간단한 방식이다.  
					 */
					StringBuilder sb = new StringBuilder();
					sb.append(now.charAt(0));
					int nowindex = 1;
					while (nowindex < now.length() - 1)
					{
						if (nowindex % 2 == 0)
						{
							sb.append(now.charAt(nowindex - 1));
						}
						else
						{
							if (nowindex + 2 == now.length())
							{
								sb.append(now.charAt(nowindex));
							}
							else
							{
								sb.append(now.charAt(nowindex + 1));
							}
						}
						++nowindex;
					}
					sb.append(now.charAt(now.length() - 1));
					result.append(sb.toString());
				}
				/*
				 * now에 뭔가 쌓였지만, 연속된 한글이 4글자가 아니라면 아쉽지만 now 그대로 result에 붙여준다.
				 */
				else
				{
					result.append(now.toString());
				}
			}
			// 공교롭게도 남은 문자가 없이 이대로 끝이라면, 바로 result를 반환.
			if (rest.length() <= index)
			{
				return result.toString();
			}
			else
			{
				/*
				 * 공백이 아니면서 한글도 아닌, 숫자나 외국어 등은 순서 그대로 result에 넣어준다.
				 * 공백들은 어차피 맨 처음에 처리를 할테고(공백은 단어의 구분으로 보면 된다.),
				 * 숫자는 뒤집으면 그 의미가 크게 상실되며,
				 * 외국어 역시 한국인 기준 모국어가 아닌 언어는 순서 재배치에도 가독성을 유지하는 현상을 얻을 수 없으므로 생략하여 그대로 result에 넣는다.
				 * (tab문자나 줄바꿈 문자 등도 여전히 그대로 result에 넣어진다. 공백문자처럼 연속되게 효율적으로 처리하진 않지만.)
				 */
				while (index < rest.length())
				{
					if (rest.charAt(index) != ' ' && (rest.charAt(index) < '가' || rest.charAt(index) > '힣'))
					{
						result.append(rest.charAt(index++));
					}
					else
					{
						break;
					}
				}
				// 공교롭게도 남은 문자가 없이 이대로 끝이라면, 바로 result를 반환.
				if (rest.length() <= index)
				{
					return result.toString();
				}
				// 남은 문자대로 끊어서 다시 반복한다.
				else
				{
					rest = rest.substring(index);
				}
			}
		}
		return result.toString();
	}
}
