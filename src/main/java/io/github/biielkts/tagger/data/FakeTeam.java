package io.github.biielkts.tagger.data;

import java.util.ArrayList;

public class FakeTeam {

    private static String UNIQUE_ID;
    private static int ID;

    static {
        UNIQUE_ID = generateUUID();
        FakeTeam.ID = 0;
    }

    private ArrayList<String> members;
    private String name;
    private String prefix;
    private String suffix;

    public FakeTeam(String prefix, String suffix, int sortPriority, boolean playerTag) {
        this.members = new ArrayList<>();
        this.prefix = "";
        this.suffix = "";
        this.name = FakeTeam.UNIQUE_ID + "_" + this.getNameFromInput(sortPriority) + ++FakeTeam.ID + (playerTag ? "+P" : "");
        this.name = ((this.name.length() > 16) ? this.name.substring(0, 16) : this.name);
        this.prefix = prefix;
        this.suffix = suffix;
    }

    protected static String generateUUID() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; ++i) {
            builder.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return builder.toString();
    }

    public void addMember(String player) {
        if (!this.members.contains(player)) {
            this.members.add(player);
        }
    }

    public boolean isSimilar(String prefix, String suffix) {
        return this.prefix.equals(prefix) && this.suffix.equals(suffix);
    }

    private String getNameFromInput(int input) {
        if (input < 0) {
            return "Z";
        }
        char letter = (char) (input / 5 + 65);
        int repeat = input % 5 + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < repeat; ++i) {
            builder.append(letter);
        }
        return builder.toString();
    }

    public ArrayList<String> getMembers() {
        return this.members;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FakeTeam)) {
            return false;
        }
        FakeTeam other = (FakeTeam) o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$members = this.getMembers();
        Object other$members = other.getMembers();
        Label_0065:
        {
            if (this$members == null) {
                if (other$members == null) {
                    break Label_0065;
                }
            } else if (this$members.equals(other$members)) {
                break Label_0065;
            }
            return false;
        }
        Object this$name = this.getName();
        Object other$name = other.getName();
        Label_0102:
        {
            if (this$name == null) {
                if (other$name == null) {
                    break Label_0102;
                }
            } else if (this$name.equals(other$name)) {
                break Label_0102;
            }
            return false;
        }
        Object this$prefix = this.getPrefix();
        Object other$prefix = other.getPrefix();
        Label_0139:
        {
            if (this$prefix == null) {
                if (other$prefix == null) {
                    break Label_0139;
                }
            } else if (this$prefix.equals(other$prefix)) {
                break Label_0139;
            }
            return false;
        }
        Object this$suffix = this.getSuffix();
        Object other$suffix = other.getSuffix();
        if (this$suffix == null) {
            return other$suffix == null;
        } else return this$suffix.equals(other$suffix);
    }

    protected boolean canEqual(Object other) {
        return other instanceof FakeTeam;
    }

    @Override
    public int hashCode() {
        int result = 1;
        Object $members = this.getMembers();
        result = result * 59 + (($members == null) ? 0 : $members.hashCode());
        Object $name = this.getName();
        result = result * 59 + (($name == null) ? 0 : $name.hashCode());
        Object $prefix = this.getPrefix();
        result = result * 59 + (($prefix == null) ? 0 : $prefix.hashCode());
        Object $suffix = this.getSuffix();
        result = result * 59 + (($suffix == null) ? 0 : $suffix.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "FakeTeam(members=" + this.getMembers() + ", name=" + this.getName() + ", prefix=" + this.getPrefix()
                + ", suffix=" + this.getSuffix() + ")";
    }
}
