def split_log_game()
    @log = File.read('./log/quake.log').split('InitGame:')
    @log.size
end

def split_line(i)
    @log[i].split("\n")
end

def game_name(i)
    @json["game_#{i.to_s}"] = {}
end

def game_logic_filters(game_block)
    game_block.each do |line|
        player_name(line)        if line.include? 'ClientUserinfoChanged:'
        kill_player(false, line) if line.include? 'Kill:'   && '<world>'
        kill_player(true, line)  if (line.include? 'Kill:') && (!line.include? '<world>')
    end
end

def player_name(line)
    player_number = split_client(line)
    include_player_number_array(player_number)
    name = get_name()
    include_player_name_array(name)
end

def kill_player(status, line)
    player_status = split_kill_line(line)
    weapon = line.split.last 
    score_kill(player_status, status)
    @total_kills += 1
    weapon_count_kill(weapon)
end

def json_structure(json)
    json['total_kills'] = @total_kills
    json['players'] = @players_name
    json['kills'] = @player_kills
    json['kills_by_means'] = @kills_by_means
    json
end

def create_json_file()
    result = @json.to_json
    File.open("./result/result.json", "w") { |f| f.write result }
end

private

def split_client(line)
    @user_line = line.split('ClientUserinfoChanged: ')
    player =  @user_line.last.split
    player[0]
end

def include_player_number_array(player_number)
    if !@players_number.include? player_number
        @players_number << player_number
    end
end

def include_player_name_array(name)
    if !@players_name.include? name
        @players_name << name
        @player_kills[name] = 0
    end
end

def get_name()
    name = @user_line.last
    name = name.split(/n\\|\\t/)
    name[1]
end

def split_kill_line(line)
    status = line.split(':')
    status = status[2].split
    status[1]
end

def score_kill(player_status, status)
    index = @players_number.find_index(player_status)
    name = @players_name[index]
    value = status == true ? 1 : -1
    less_value = @player_kills[name] + value
    @player_kills[name] = less_value
end

def weapon_count_kill(weapon)
    if !@weapons.include? weapon
        @weapons << weapon
        @kills_by_means[weapon] = 1
    else
        index_weapon = @weapons.find_index(weapon)
        index_weapons = @weapons[index_weapon]
        @kills_by_means[index_weapons] += + 1
    end
end