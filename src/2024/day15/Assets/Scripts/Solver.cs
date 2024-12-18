using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using UnityEngine;
using UnityEngine.Tilemaps;

public class Solver : MonoBehaviour
{
    public TextAsset textFile;
    public Tilemap tilemap;
    public bool doubleSizeMode = false;
    
    public GameObject playerPrefab, wallPrefab, boxPrefab;
    
    private List<Vector2Int> _walls = new();
    private List<Vector2Int> _boxes = new();
    private Vector2Int? _playerPosition;
    private Player _player;
    private List<Box> _boxObjects = new();
    
    // Start is called before the first frame update
    void Start()
    {
        var input = textFile.text.Split("\r\n\r\n");
        var gridRows = input[0].Split("\r\n");
        var movements = string.Join("", input[1].Split("\r\n"));
        
        CalculateEntityPositions(gridRows);
        PlaceEntities();
        StartCoroutine(PerformMovements(movements));
    }

    void CalculateEntityPositions(string[] gridRows)
    {
        for (var rowIndex = 0; rowIndex < gridRows.Length; rowIndex++)
        {
            var row = doubleSizeMode
                ? gridRows[rowIndex].Replace("#", "##").Replace("O", "[]").Replace(".", "..").Replace("@", "@.")
                : gridRows[rowIndex];

            for (var colIndex = 0; colIndex < row.Length; colIndex++)
            {
                var cell = row[colIndex];
                var point = new Vector2Int(colIndex, -rowIndex);

                if (cell == '#') _walls.Add(point);
                else if (cell is '[' or 'O') _boxes.Add(point);
                else if (cell == '@') _playerPosition = point;
            }
        }
    }

    void PlaceEntities()
    {
        foreach (var wallPosition in _walls)
        {
            Instantiate(wallPrefab, (Vector2)wallPosition, Quaternion.identity, tilemap.transform);
        }

        _player = Instantiate(playerPrefab, (Vector2)_playerPosition!, Quaternion.identity).GetComponent<Player>();

        foreach (var boxPosition in _boxes)
        {
            var shiftedPosition = doubleSizeMode ? boxPosition - new Vector2(0.5f, 0) : boxPosition;
            var boxObject = Instantiate(boxPrefab, shiftedPosition, Quaternion.identity, tilemap.transform);
            var box = boxObject.GetComponent<Box>();

            if (doubleSizeMode)
            {
                box.transform.localScale = new Vector2(box.transform.localScale.x * 2.2f, box.transform.localScale.y);
            }
            
            _boxObjects.Add(box);
            box.player = _player;
        }
        
        _player.SetBoxes(_boxObjects);
    }
    
    private IEnumerator PerformMovements(string movements)
    {
        yield return new WaitForSeconds(2);
        
        var movementsChars = movements.ToCharArray();
        
        for (var index = 0; index < movementsChars.Length; index++)
        {
            var movement = movementsChars[index];
            
            var direction = movement switch
            {
                'v' => Vector2.down,
                '>' => Vector2.right,
                '^' => Vector2.up,
                '<' => Vector2.left,
                _ => throw new InvalidDataException()
            };

            yield return _player.MovePlayer(direction);
            
            var iterationPercentage = index * 100 / movementsChars.Length;

            if (iterationPercentage % 10 == 0)
            {
                print($"Finished {iterationPercentage}%");
            }
        }

        var boxesGPSes = _boxObjects.Select(box =>
        {
            var position = box.transform.position;

            return Mathf.Abs(Mathf.Floor(position.x)) + Mathf.Abs(Mathf.Floor(position.y)) * 100;
        });
        
        print($"Day 15, part 1: {boxesGPSes.Sum()}");
    }
}
