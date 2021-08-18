package io.github.biielkts.tagger.packets;

import lombok.Getter;

@Getter
public enum PacketData {

    v1_7("e", "c", "d", "a", "f", "g", "b", "NA", "NA"),
    cauldron("field_149317_e", "field_149319_c", "field_149316_d", "field_149320_a", "field_149314_f", "field_149315_g", "field_149318_b", "NA", "NA"),
    v1_8("g", "c", "d", "a", "h", "i", "b", "NA", "e"),
    v1_9("h", "c", "d", "a", "i", "j", "b", "f", "e"),
    v1_10("h", "c", "d", "a", "i", "j", "b", "f", "e"),
    v1_11("h", "c", "d", "a", "i", "j", "b", "f", "e"),
    v1_12("h", "c", "d", "a", "i", "j", "b", "f", "e");

    private String members;
    private String prefix;
    private String suffix;
    private String teamName;
    private String paramInt;
    private String packOption;
    private String displayName;
    private String push;
    private String visibility;

    PacketData(String members, String prefix, String suffix, String teamName, String paramInt, String packOption, String displayName, String push, String visibility) {
        this.members = members;
        this.prefix = prefix;
        this.suffix = suffix;
        this.teamName = teamName;
        this.paramInt = paramInt;
        this.packOption = packOption;
        this.displayName = displayName;
        this.push = push;
        this.visibility = visibility;
    }
}
