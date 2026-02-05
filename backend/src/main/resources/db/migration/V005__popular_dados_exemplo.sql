INSERT INTO artistas (id, nome, tipo) VALUES
('b7c83868-909b-4088-af21-09bd738f434a', 'Serj Tankian', 'CANTOR'),
('a1b2c3d4-e5f6-4789-8012-34567890abcd', 'Mike Shinoda', 'CANTOR'),
('11111111-2222-3333-4444-555555555555', 'Michel Teló', 'CANTOR'),
('99999999-8888-7777-6666-555555555555', 'Guns N'' Roses', 'BANDA');

INSERT INTO albuns (id, titulo, ano_lancamento) VALUES
('aa11bb22-cc33-dd44-ee55-ff6677889900', 'Harakiri', 2012),
('bb22cc33-dd44-ee55-ff66-77889900aa11', 'Black Blooms', 2020),
('cc33dd44-ee55-ff66-7788-9900aa11bb22', 'The Rough Dog', 2017),
('dd44ee55-ff66-7788-9900-aa11bb22cc33', 'The Rising Tied', 2005),
('ee55ff66-7788-9900-aa11-bb22cc33dd44', 'Post Traumatic', 2018),
('ff667788-9900-aa11-bb22-cc33dd44ee55', 'Post Traumatic EP', 2018),
('00112233-4455-6677-8899-aabbccddeeff', 'Where''d You Go', 2006),
('12345678-1234-1234-1234-123456789012', 'Bem Sertanejo', 2010),
('87654321-4321-4321-4321-210987654321', 'Bem Sertanejo - O Show (Ao Vivo)', 2011),
('abcdef01-2345-6789-abcd-ef0123456789', 'Bem Sertanejo - (1ª Temporada) - EP', 2010),
('baddecaf-1234-5678-90ab-cdef12345678', 'Use Your Illusion I', 1991),
('cafebabe-1234-5678-90ab-cdef12345678', 'Use Your Illusion II', 1991),
('deadbeef-1234-5678-90ab-cdef12345678', 'Greatest Hits', 2004);

INSERT INTO artista_album (artista_id, album_id) VALUES
('b7c83868-909b-4088-af21-09bd738f434a', 'aa11bb22-cc33-dd44-ee55-ff6677889900'), -- Serj -> Harakiri
('b7c83868-909b-4088-af21-09bd738f434a', 'bb22cc33-dd44-ee55-ff66-77889900aa11'), -- Serj -> Black Blooms
('b7c83868-909b-4088-af21-09bd738f434a', 'cc33dd44-ee55-ff66-7788-9900aa11bb22'), -- Serj -> Rough Dog
('a1b2c3d4-e5f6-4789-8012-34567890abcd', 'dd44ee55-ff66-7788-9900-aa11bb22cc33'), -- Mike -> Rising Tied
('a1b2c3d4-e5f6-4789-8012-34567890abcd', 'ee55ff66-7788-9900-aa11-bb22cc33dd44'), -- Mike -> Post Traumatic
('a1b2c3d4-e5f6-4789-8012-34567890abcd', 'ff667788-9900-aa11-bb22-cc33dd44ee55'), -- Mike -> PT EP
('a1b2c3d4-e5f6-4789-8012-34567890abcd', '00112233-4455-6677-8899-aabbccddeeff'), -- Mike -> Where'd You Go
('11111111-2222-3333-4444-555555555555', '12345678-1234-1234-1234-123456789012'), -- Michel -> Bem Sertanejo
('11111111-2222-3333-4444-555555555555', '87654321-4321-4321-4321-210987654321'), -- Michel -> Show
('11111111-2222-3333-4444-555555555555', 'abcdef01-2345-6789-abcd-ef0123456789'), -- Michel -> EP Temp
('99999999-8888-7777-6666-555555555555', 'baddecaf-1234-5678-90ab-cdef12345678'), -- Guns -> UI 1
('99999999-8888-7777-6666-555555555555', 'cafebabe-1234-5678-90ab-cdef12345678'), -- Guns -> UI 2
('99999999-8888-7777-6666-555555555555', 'deadbeef-1234-5678-90ab-cdef12345678'); -- Guns -> Hits
