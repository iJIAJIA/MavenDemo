package timezone;

import java.time.ZoneOffset;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;

public class TimeZoneOffsetTest {
	
	/**
	 * ZoneOffset的描述： A time-zone offset is the amount of time that a time-zone differs from Greenwich/UTC. </br>
	 * <p>ZoneOffset是 <b>当地时间-格林尼治</b>的时间差</p> </br>
	 * javascript的 new Date().getTimezoneOffset(); 是 <b>格林尼治-当地时间</b>的 时间差，精确到分钟。</br>
	 * <p>从前端获取TimezoneOffset时需要进行<b>正负切换</b></p>
	 */
	@Test
	public void test() {
		// 当地时间比格林尼治早8个小时，即东8区
		ZoneOffset offset8HoursEarly = ZoneOffset.ofHoursMinutes(8, 00);
		TimeZone timeZone8HourEarly = TimeZone.getTimeZone(offset8HoursEarly);
		// 北京的时区
		TimeZone beijingTimeZone = TimeZone.getTimeZone("Asia/Shanghai");
		// 两者跟格林尼治的偏移量是相等的 8小时
		Assert.assertEquals(timeZone8HourEarly.getRawOffset(), beijingTimeZone.getRawOffset());
		// javascript的 new Date().getTimezoneOffset(); 是 <b>格林尼治-当地时间</b>的 时间差，精确到分钟。</br>
		// 这里为东8区
		int offsetFromJs = -480;
		// 进行正负转换
		int offsetInJava = -offsetFromJs;
		ZoneOffset zoneOffset = ZoneOffset.ofHoursMinutes(offsetInJava /60 , offsetInJava % 60);
		TimeZone timeZone = TimeZone.getTimeZone(zoneOffset);
		// 与 北京跟格林尼治的偏移量是一样的
		Assert.assertEquals(timeZone.getRawOffset(), beijingTimeZone.getRawOffset());
	}
	
	/**
	 * 计算当地零点时间戳
	 * unix时间戳 是格林威治时间1970年01月01日00时00分00秒(北京时间1970年01月01日08时00分00秒)起至现在的总秒数
	 * 
	 */
	@Test
	public void test2() {
		long current = System.currentTimeMillis();
		// 前提：unix时间戳 是格林威治时间1970年01月01日00时00分00秒起至现在的总秒数
		// 每个地方计算unix时间戳的起始时间不一样 北京计算起始时间为1970年01月01日08时00分00秒
		// 以天为周期1000*3600*24， current/1000*3600*24 获取至今的天数
		// result以天为单位运算得出的整天数时间戳，此时间戳为格林尼治的零点时间戳
		// 因为时区问题，在北京此时的result时间戳对应北京的早上8点
		// 因此需要执行result-timezoneOffset 减掉时区偏移量，从而得出北京的零点时间戳
		long result = current/(1000*3600*24)*(1000*3600*24);
		long timezoneOffset = TimeZone.getDefault().getRawOffset();
		System.out.printf("result:%d,offset:%d,sum:%d",result,timezoneOffset,result-timezoneOffset);
	}
	
	@Test
	public void test3() {
		Assert.assertEquals(-8,-480 / 60);
		Assert.assertEquals(-1,-481 % 60);
	}
	
}
