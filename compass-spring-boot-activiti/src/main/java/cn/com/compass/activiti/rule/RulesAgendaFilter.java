package cn.com.compass.activiti.rule;

import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wanmk
 * @git https://gitee.com/milkove
 * @email 524623302@qq.com
 * @todo
 * @date 2019/3/7 14:52
 */
public class RulesAgendaFilter implements AgendaFilter {

    protected Set<String> suffixSet = new HashSet<>();
    protected boolean accept;

    /**
     * Determine if a given match should be fired.
     *
     * @param match The match that is requested to be fired
     * @return boolean value of "true" accepts the match for firing.
     */
    @Override
    public boolean accept(Match match) {
        return suffixSet.contains(match.getRule().getName())?this.accept:!this.accept;
    }

    public void addSuffic(String suffix) {
        this.suffixSet.add(suffix);
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }
}
